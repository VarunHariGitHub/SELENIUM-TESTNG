import { tool } from "@opencode-ai/plugin"
import path from "path"

export default tool({
  description: "Open the latest test report in the default browser",
  args: {
    type: tool.schema.enum(["surefire", "testng", "extent"]).optional().describe("Report type: surefire (index.html), testng (testng-results.xml), extent (ExtentReport) — default: surefire"),
  },
  async execute(args, context) {
    const projectRoot = context.worktree

    if (args.type === "extent") {
      const reportPath = path.join(projectRoot, "target", "extent-reports", "index.html")
      try {
        await Bun.file(reportPath).text()
        const uri = `file:///${reportPath.replace(/\\/g, "/")}`
        await Bun.$`powershell -Command "Start-Process '${uri}'"`.text()
        return `Opened Extent Report at ${uri}`
      } catch {
        return `⚠ No Extent Report found. Run tests first.`
      }
    }

    if (args.type === "testng") {
      const reportPath = path.join(projectRoot, "target", "surefire-reports", "testng-results.xml")
      try {
        await Bun.file(reportPath).text()
        await Bun.$`powershell -Command "Start-Process '${reportPath}'"`.text()
        return `Opened testng-results.xml at ${reportPath}`
      } catch {
        return `⚠ No testng-results.xml found. Run tests first.`
      }
    }

    const reportPath = path.join(projectRoot, "target", "surefire-reports", "index.html")
    try {
      await Bun.file(reportPath).text()
      const uri = `file:///${reportPath.replace(/\\/g, "/")}`
      await Bun.$`powershell -Command "Start-Process '${uri}'"`.text()
      return `Opened surefire report at ${uri}`
    } catch {
      return `⚠ No surefire report found. Run tests first (e.g. 'run the smoke tests').`
    }
  },
})
