package de.codelett.amiberry.ui.startup

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import java.io.File
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class StartupViewModel(application: Application) : AndroidViewModel(application) {

    /**
     * Name of zip file containing all images, configs, directory structure, etc.
     *  necessary to get the emulation core up and running
     * The file can be found in the assets directory of this android project
     */
    private val amiberryDataZipFilename = "data.zip"

    public fun extractGameContent() {
        val application: Application = this.getApplication();
        val targetDir = application.getExternalFilesDir(null)
                ?: throw Exception("Assets could not be extracted. External files dir isn't accessible.")

        application.assets.open(amiberryDataZipFilename).use {
            ZipInputStream(it).use {
                var zipEntry = it.nextEntry
                while (zipEntry != null) {
                    Log.v(this::class.simpleName, "Found ${zipEntry.name}")
                    val targetFile = File("${targetDir}/${zipEntry.name}")

                    if (zipEntry.isDirectory) {
                        ensureDirectoryExists(targetFile);
                    }
                    else {
                        writeFileFromZipStreamToFileOnce(it, zipEntry, targetFile)
                    }

                    it.closeEntry()             // Closes the current ZIP entry and positions the stream for reading the next entry.
                    zipEntry = it.nextEntry     // Reads the next ZIP file entry and positions the stream at the beginning of the entry data.
                }
            }
        }
    }

    private fun ensureDirectoryExists(targetDir: File) {
        if (targetDir.exists()) {
            Log.v(this::class.simpleName, "Exists ${targetDir.absolutePath}")
            if (!targetDir.isDirectory) {
                throw Exception("Assets could not be extracted. Couldn't create directory, because a file with name already exists: ${targetDir.absolutePath}")
            }
            return
        }

        if (!targetDir.mkdirs()) {
            Log.v(this::class.simpleName, "Creating ${targetDir.absolutePath}")
            throw Exception("Assets could not be extracted. Creating directory failed: ${targetDir.absolutePath}")
        }
    }

    /**
     * Writes a File contained in a ZIP file to a file.
     * Won't overwrite existing files (writes only once).
     *
     * @remark ZipInputStream.nextEntry always moves to the next entry, so we cannot use that to get the current ZipEntry
     */
    private fun writeFileFromZipStreamToFileOnce(zipStream: ZipInputStream, zipEntry: ZipEntry, file: File)
    {
        if(zipEntry.isDirectory)
        {
            throw IllegalArgumentException("ZipEntry is a directory. This function can't create directories. ZipEntry name: ${zipEntry.name}")
        }

        if(file.exists())
        {
            Log.v(this::class.simpleName, "Skipping existing ${zipEntry.name}")
            return
        }

        Log.v(this::class.simpleName, "Trying to write ${zipEntry.name}")
        FileOutputStream(file).use {
            zipStream.copyTo(it)
        }
    }
}