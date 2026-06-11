import { tool } from "@opencode-ai/plugin"
import path from "path"

export default tool({
  description: "Run the smoke test suite (mvn clean test -Dgroups=smoke) and return structured results",
  args: {},
  async execute(_args, context) {
    const projectRoot = context.worktree
    const reportFile = path.join(projectRoot, "target", "surefire-reports", "testng-results.xml")

    const shellOutput = await Bun.$`cd ${projectRoot} && mvn clean test -Dgroups=smoke`.text()

    let passed = 0, failed = 0, skipped = 0, total = 0
    const failureDetails: string[] = []

    try {
      const content = await Bun.file(reportFile).text()
      const summaryMatch = content.match(/<testng-results[^>]*total="(\d+)"[^>]*passed="(\d+)"[^>]*failed="(\d+)"[^>]*skipped="(\d+)"/)
      if (summaryMatch) {
        total = parseInt(summaryMatch[1])
        passed = parseInt(summaryMatch[2])
        failed = parseInt(summaryMatch[3])
        skipped = parseInt(summaryMatch[4])
      }

      const failRegex = /<test-method[^>]*status="FAIL"[^>]*name="([^"]+)"[^>]*class="([^"]+)"/g
      let match: RegExpExecArray | null
      while ((match = failRegex.exec(content)) !== null) {
        failureDetails.push(`${match[2]}.${match[1]}`)
      }
    } catch {
      return `⚠ Report parsing failed. Raw output:\n${shellOutput}`
    }

    const lines = [`## Smoke Test Results`, ``, `**Summary:** ${passed}/${total} passed, ${failed} failed, ${skipped} skipped`]
    if (failed > 0) {
      lines.push(``, `**Failed tests:**`)
      for (const f of failureDetails) {
        lines.push(`- ${f}`)
      }
    }
    lines.push(``, `### Full Output`, ``, "```", shellOutput, "```")

    return lines.join("\n")
  },
})
