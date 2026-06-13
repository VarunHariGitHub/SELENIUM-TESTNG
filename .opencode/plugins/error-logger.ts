import type { Plugin } from "@opencode-ai/plugin"
import fs from "fs"
import path from "path"

export const ErrorLogger: Plugin = async ({ directory }) => {
  const logFile = path.join(directory, ".opencode", "error-log.md")

  return {
    event: async ({ event }) => {
      if (event.type !== "session.error") return

      const timestamp = new Date().toISOString().replace("T", " ").substring(0, 19)
      const message = event.properties.error?.data?.message || "Unknown error"
      const entry = `- **${timestamp}** | ${message}\n`

      try {
        fs.appendFileSync(logFile, entry, "utf8")
      } catch {
        fs.writeFileSync(logFile, `# Error Log\n\n${entry}`, "utf8")
      }
    },
  }
}
