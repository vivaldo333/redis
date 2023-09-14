package net.ks.utils

import java.io.{File, FileNotFoundException, InputStream}
import scala.io.Source

object FileUtils {
  val delimiter = File.separator

  def getFileData(filePath: String): String = {
    val inputStream: InputStream = null
    try {
      val inputStream = getClass.getResourceAsStream(filePath)
      Source.fromInputStream(inputStream).mkString
    } catch {
      case ex@(_: FileNotFoundException) =>  throw new RuntimeException(ex.toString)
      case e: Throwable => throw new RuntimeException(e)
    } finally {
      if (inputStream != null) inputStream.close()
    }
  }
}
