package org.ed.combiner

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.fileEditor.FileEditorManager
import com.intellij.openapi.vfs.VirtualFile
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection
import java.io.IOException
import java.nio.charset.StandardCharsets
import java.util.ArrayList

class CombineSelectedFilesAction : CombineCopyFilesActionBase() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val files = e.getData(CommonDataKeys.VIRTUAL_FILE_ARRAY) ?: return

        val allFiles = ArrayList<VirtualFile>()
        for (file in files) {
            val itemFiles = collectFiles(file)
            allFiles.addAll(itemFiles)
        }

        val combinedContent = convertFilesToString(allFiles, project.basePath);
        copyToClipboard(combinedContent)
    }
}

class CombineOpenedFilesAction : CombineCopyFilesActionBase() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val fileEditorManager = FileEditorManager.getInstance(project)
        val openedFiles = fileEditorManager.openFiles

        val allFiles = ArrayList<VirtualFile>()
        for (file in openedFiles) {
            val itemFiles = collectFiles(file)
            allFiles.addAll(itemFiles)
        }

        val combinedContent = convertFilesToString(allFiles, project.basePath)
        copyToClipboard(combinedContent)
    }

}

abstract class CombineCopyFilesActionBase : AnAction() {

    protected fun copyToClipboard(content: String) {
        val stringSelection = StringSelection(content)
        val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
        clipboard.setContents(stringSelection, null)
    }

    protected fun convertFilesToString(files: ArrayList<VirtualFile>, projectBasePath: String?): String {
        val sb = StringBuilder()
        for (file in files) {
            try {
                val relativeFilePath = getRelativePath(projectBasePath, file.path)
                val content = String(file.contentsToByteArray(), StandardCharsets.UTF_8)
                val languageId = getLanguageIdentifier(file)

                sb.append(relativeFilePath)
                sb.append("\n```")
                if (languageId.isNotEmpty()) {
                    sb.append(languageId)
                }
                sb.append("\n")
                sb.append(content)
                sb.append("\n```\n\n")
            } catch (ex: IOException) {
                sb.append("// Error reading ${file.path}: ${ex.message}\n\n")
                ex.printStackTrace()
            }
        }

        return sb.toString()
    }

    private fun getRelativePath(basePath: String?, absolutePath: String): String {
        if (basePath.isNullOrEmpty()) {
            return absolutePath
        }

        return if (absolutePath.startsWith(basePath)) {
            absolutePath.substring(basePath.length).removePrefix("/")
        } else {
            absolutePath
        }
    }

    protected fun collectFiles(file: VirtualFile): ArrayList<VirtualFile> {

        val fileList = ArrayList<VirtualFile>()

        if (file.isDirectory) {
            for (child in file.children) {
                val list = collectFiles(child)
                fileList.addAll(list)
            }
        } else {
            fileList.add(file)
        }

        return fileList
    }

    private fun getLanguageIdentifier(file: VirtualFile): String {
        val extension = file.extension?.lowercase() ?: return ""

        return when (extension) {
            "java" -> "java"
            "kt" -> "kotlin"
            "kts" -> "kotlin"
            "cs" -> "csharp"
            "py" -> "python"
            "js" -> "javascript"
            "ts" -> "typescript"
            "html" -> "html"
            "http" -> "http"
            "css" -> "css"
            "scss" -> "scss"
            "less" -> "less"
            "json" -> "json"
            "xml" -> "xml"
            "yaml", "yml" -> "yaml"
            "md" -> "markdown"
            "sh" -> "bash"
            "bat" -> "batch"
            "ps1" -> "powershell"
            "sql" -> "sql"
            "rb" -> "ruby"
            "php" -> "php"
            "go" -> "go"
            "rust", "rs" -> "rust"
            "swift" -> "swift"
            "c" -> "c"
            "cpp", "cc", "cxx" -> "cpp"
            "h", "hpp" -> "cpp"
            "scala" -> "scala"
            "dart" -> "dart"
            "groovy" -> "groovy"
            "r" -> "r"
            "pl" -> "perl"
            "lua" -> "lua"
            "gradle" -> "gradle"
            "tf", "tfvars" -> "terraform"
            "conf" -> "conf"
            "properties" -> "properties"
            "toml" -> "toml"
            "tex" -> "latex"
            else -> ""
        }
    }
}