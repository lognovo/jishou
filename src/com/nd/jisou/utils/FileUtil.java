
package com.nd.jisou.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import android.R.bool;
import android.nfc.Tag;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class FileUtil {

	private static final String TAG = "FileUtil";
	public static final String FILE_SEPARATOR = "_";

	/**
	 * 获取单个文件的MD5值！
	 * 
	 * @param file
	 * @return
	 */
	public static String getFileMD5(String path) {
		File file = new File(path);
		if (!file.isFile()) {
			return null;
		}
		MessageDigest digest = null;
		FileInputStream in = null;
		byte buffer[] = new byte[1024];
		int len;
		try {
			digest = MessageDigest.getInstance("MD5");
			in = new FileInputStream(file);
			while ((len = in.read(buffer, 0, 1024)) != -1) {
				digest.update(buffer, 0, len);
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		BigInteger bigInt = new BigInteger(1, digest.digest());
		return bigInt.toString(16);
	}

	/**
	 * 计算文件夹大小
	 */
	public static final long getSize(File dir) {
		long retSize = 0;
		if (dir == null) {
			return retSize;
		}
		if (dir.isFile()) {
			return dir.length();
		}
		File[] entries = dir.listFiles();
		if (entries == null) {
			return retSize;
		}
		int count = entries.length;
		for (int i = 0; i < count; i++) {
			if (entries[i].isDirectory()) {
				retSize += getSize(entries[i]);
			} else {
				retSize += entries[i].length();
			}
		}
		return retSize;
	}
	
	/**
	 * 
	 *  函数名称 : getLastModified
	 *  功能描述 :  获取修改时间
	 *  参数及返回值说明：
	 *  	@param dir
	 *  	@return
	 */
	public static final long getLastModified(File dir){
		long lastModified = 0;
		if (dir == null) {
			return lastModified;
		}
		if (dir.isFile()) {
			return dir.lastModified();
		}else {
			return lastModified;
		}
	}

	/**
	 * 创建文件
	 * 
	 * @param pathName
	 *            路径名
	 * @return void
	 */
	public static void createFile(File file) {
		// File file = new File(pathName);
		if (!file.getParentFile().exists()) {
			System.out.println("文件所在目录不存在，准备创建...");
			if (file.getParentFile().mkdirs()) {
				System.out.println("目录创建成功，准备创建文件...");
				try {
					if (file.createNewFile()) {
						System.out.println(file.getAbsolutePath() + "创建成功！");
						return;
					} else {
						System.out.println("文件创建失败，退出!");
						return;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("目录创建失败，退出!");
				return;
			}
		} else {
			System.out.println("准备创建文件...");
			try {
				if (file.createNewFile()) {
					System.out.println(file.getAbsolutePath() + "创建成功!");
					return;
				} else {
					System.out.println("文件创建失败，退出!");
					return;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将字符串写入到文件中
	 * 
	 * @param file
	 * @param txt
	 * @throws Exception
	 */
	public static void stringWriteToFile(File file, String txt)
			throws Exception {
		BufferedReader reader = null;
		PrintWriter writer = null;
		if (!file.exists()) {
			FileUtil.createFile(file);
		}
		try {
			String s;
			reader = new BufferedReader(new StringReader(txt));
			writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			while ((s = reader.readLine()) != null)
				writer.println(s);
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @Title: stringWriteToFile
	 * @Description: 追加到文件末尾
	 * @param file
	 * @param txt
	 * @throws Exception
	 * @throws
	 */
	public static void stringWriteToFileLast(File file, String txt)
			throws Exception {
		BufferedReader reader = null;
		BufferedWriter writer = null;
		if (!file.exists()) {
			FileUtil.createFile(file);
		}
		try {
			String s;
			reader = new BufferedReader(new StringReader(txt));
			writer = new BufferedWriter(new FileWriter(file, true));
			while ((s = reader.readLine()) != null) {
				writer.append(s);
				writer.newLine();
			}
			writer.flush();
		} catch (Exception ex) {
			throw ex;
		} finally {
			try {
				reader.close();
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 重命名文件
	 * 
	 * @param fileName
	 * @param name
	 * @return
	 */
	public static boolean rename(String fileName, String name) {
		File file = new File(fileName);
		if (file.getParent() == null) {
			File newFile = new File(name);
			return file.renameTo(newFile);
		} else {
			File newFile = new File(file.getParent(), name);
			return file.renameTo(newFile);
		}
	}

	/**
	 * 移动文件或文件夹
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static boolean moveTo(String oldPath, String newPath) {
		File file = new File(oldPath);
		File newFile = new File(newPath);
		if (newFile.exists()) {
			return false;
		} else {
			boolean result = file.renameTo(newFile);
			return result;
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param src
	 * @param dest
	 * @return
	 */
	public static boolean copyFile(File src, File dest) {
		// 新建文件输入流并对它进行缓冲
		FileInputStream input = null;
		BufferedInputStream inBuff = null;

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;
		try {
			input = new FileInputStream(src);
			inBuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			output = new FileOutputStream(dest);
			outBuff = new BufferedOutputStream(output);
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				inBuff.close();
				outBuff.close();
				output.close();
				input.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * 对文件流进行拷贝
	 * 
	 * @param input
	 * @param dest
	 * @return
	 */
	public static boolean copyFile(InputStream input, File dest) {
		// 新建文件输入流并对它进行缓冲
		BufferedInputStream inBuff = null;

		// 新建文件输出流并对它进行缓冲
		FileOutputStream output = null;
		BufferedOutputStream outBuff = null;
		try {
			inBuff = new BufferedInputStream(input);

			// 新建文件输出流并对它进行缓冲
			output = new FileOutputStream(dest);
			outBuff = new BufferedOutputStream(output);
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			try {
				inBuff.close();
				outBuff.close();
				output.close();
				input.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	/**
	 * 复制单个文件
	 * 
	 * @param oldPathFile
	 *            准备复制的文件源
	 * @param newPathFile
	 *            拷贝到新绝对路径带文件名
	 * @return
	 */
	private static boolean copyFile(String oldPathFile, String newPathFile) {
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPathFile);
			File newFile = new File(newPathFile);
			if (!newFile.exists()) {
				FileUtil.createFile(newFile);
			}
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(oldPathFile); // 读入原文件
				FileOutputStream fs = new FileOutputStream(newPathFile);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // 字节数 文件大小
					System.out.println(bytesum);
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
			return true;
		} catch (Exception e) {
			delele(newPathFile);
			return false;
		}
	}

	/**
	 * 复制整个文件夹的内容
	 * 
	 * @param oldPath
	 *            准备拷贝的目录
	 * @param newPath
	 *            指定绝对路径的新目录
	 * @return
	 */
	private static boolean copyFolder(String oldPath, String newPath) {
		try {
			new File(newPath).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}
				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 复制文件或者目录
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return
	 */
	public static boolean copy(String oldPath, String newPath) {
		File file = new File(oldPath);
		if (file.isDirectory()) {
			return copyFolder(oldPath, newPath);
		} else {
			return copyFile(oldPath, newPath);
		}
	}

	/**
	 * 删除文件或文件夹
	 * 
	 * @param filePath
	 * @return
	 */
	public static boolean delele(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			return true;
		}
		if (file.isDirectory()) {
			return delFolder(filePath);
		} else {
			return delFile(filePath);
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param filePath
	 * @return
	 */
	private static boolean delFile(String filePath) {
		try {
			File myDelFile = new File(filePath);
			myDelFile.delete();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除文件夹
	 * 
	 * @param folderPath
	 *            文件夹完整绝对路径
	 * @return
	 */
	private static boolean delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // 删除空文件夹
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 删除指定文件夹下所有文件
	 * 
	 * @param path
	 *            文件夹完整绝对路径
	 * @return
	 * @return
	 */
	private static boolean delAllFile(String path) {
		boolean bea = false;
		File file = new File(path);
		if (!file.exists()) {
			return bea;
		}
		if (!file.isDirectory()) {
			return bea;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				bea = true;
			}
		}
		return bea;
	}

	/**
	 * 删除文件，可以是单个文件或文件夹
	 * 
	 * @param fileName
	 *            待删除的文件名
	 * @return 文件删除成功返回true,否则返回false
	 */
	public static boolean delete(String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			// Log.i(TAG, "delete fail：" + fileName + " no exits!");
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(fileName);
			} else {
				return deleteDirectory(fileName);
			}
		}
	}

	/**
	 * 删除单个文件
	 * 
	 * @param fileName
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true,否则返回false
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			// Log.i(TAG, "delete the file" + fileName + " success!");
			return true;
		} else {
			// Log.i(TAG, "delete the file" + fileName + "fail!");
			return false;
		}
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// Log.i(TAG, "delete dir fail!" + dirFile + " no exit!");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			// Log.i(TAG, "delete dir fail!");
			return false;
		}

		// 删除当前目录
		if (dirFile.delete()) {
			// Log.i(TAG, "delete dir" + dir + "success!");
			return true;
		} else {
			// Log.i(TAG, "delete dir" + dir + "fail!");
			return false;
		}
	}

	public static long getAvailableSpace(String path) {
		StatFs sf = new StatFs(path);
		long blockSize = sf.getBlockSize();
		// long blockCount = sf.getBlockCount();
		long availCount = sf.getAvailableBlocks();
		return availCount * blockSize;
	}

	// 得到sdcard的 总大小
	public static long getTotalExternalMemorySize() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			Log.d(TAG, "getTotalExternalMemorySize:"
					+ Environment.getExternalStorageDirectory()
							.getAbsolutePath());
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory()
					.getPath());
			long blockSize = sf.getBlockSize();
			long blockCount = sf.getBlockCount();
			return blockSize * blockCount;
		}

		return 0;
	}

	// 得到sdcard的 剩余大小
	public static long getAvailableExternalMemorySize() {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			StatFs sf = new StatFs(Environment.getExternalStorageDirectory()
					.getPath());
			long blockSize = sf.getBlockSize();
			long blockAvail = sf.getAvailableBlocks();
			return blockSize * blockAvail;
		}

		return 0;
	}

	// 得到内部存储剩余大小
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory(); // 获取数据目录
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}

	// 得到内部存储总大小
	public static long getTotalInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long totalBlocks = stat.getBlockCount();
		return totalBlocks * blockSize;
	}

	/**
	 * 删除目录下的文件及目录,不删除目录
	 * 
	 * @param dir
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true,否则返回false
	 */
	public static boolean deleteFilesUnderDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			// Log.i(TAG, "delete dir fail!" + dirFile + " no exit!");
			return false;
		}
		boolean flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else {
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}

		if (!flag) {
			// Log.i(TAG, "delete dir fail!");
			return false;
		}
		return true;
	}

	/**
	 * @Title: createFile
	 * @Description: 创建目录和文件
	 * @param pathFilename
	 *            路径文件名
	 * @return File
	 * @throws
	 */
	// public static File createFile(String pathFilename) {
	// File file = new File(pathFilename);
	// //判断目录是否存在
	// if (!file.getParentFile().exists()) {
	// if (file.getParentFile().mkdirs()) {
	// try {
	// boolean ls = file.createNewFile();
	// if (!ls) {
	// return null;
	// }
	// return file;
	// } catch (IOException e) {
	// LogUtil.v("FileUtil", e.getMessage());
	// }
	// }else{//目录创建失败
	// return null;
	// }
	// } else {
	// try {
	// //删除原先的文件
	// if(file.exists()){
	// file.delete();
	// }
	// //创建文件
	// boolean ls = file.createNewFile();
	// if (!ls) {
	// return null;
	// }
	// return file;
	// } catch (IOException e) {
	// LogUtil.v("FileUtil", e.getMessage());
	//
	// }
	// }
	// return null;
	// }
	public static void streamToFile(InputStream inputStream, File destFile)
			throws IOException {

		if (destFile.exists()) {
			destFile.delete();
		}
		destFile.createNewFile();
		FileOutputStream out = new FileOutputStream(destFile);
		try {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) >= 0) {
				out.write(buffer, 0, bytesRead);
			}
		} finally {
			out.flush();
			try {
				out.getFD().sync();
			} catch (IOException e) {
			}
			out.close();
		}
	}

	/**
	 * 清理过期文件
	 * 
	 * @param dir
	 * @param timeout
	 */
	public static void clearOldFile(String dir, long timeout) {
		long now = System.currentTimeMillis();
		File file = new File(dir);
		int i = 0;
		if (file.exists()) {
			File[] files = file.listFiles();
			if (files != null) {
				for (File f : files) {
					long modify = f.lastModified();
					if ((now - modify) > timeout) {
						f.delete();
						i++;
					}
				}
			}
		}
		if (i > 0) {
			System.out.println("delete old files:" + i);
		}
	}

	/**
	 * 创建任意深度的文件所在文件夹,可以用来替代直接new File(path)。
	 * 
	 * @param path
	 * @return File对象
	 */
	public static File createFile(String path) {
		File file = new File(path);

		// 寻找父目录是否存在
		File parent = new File(file.getAbsolutePath().substring(0,
				file.getAbsolutePath().lastIndexOf(File.separator)));
		// 如果父目录不存在，则递归寻找更上一层目录
		if (!parent.exists()) {
			createFile(parent.getPath());
			// 创建父目录
			parent.mkdirs();
		}

		return file;
	}

	/**
	 * 列出所有包含includeName（后缀），并不包含excludeName（后缀）的rootPath路径下的文件
	 * 
	 * @param rootPath
	 * @param includeName
	 * @param excludeName
	 * @param includeChildDir
	 * @return
	 */
	public static List<File> listFiles(String rootPath, String[] includeName,
			String[] excludeName, boolean includeChildDir) {
		List<File> fileList = new ArrayList<File>();
		File rootFile = new File(rootPath);
		if (rootFile.exists()) {
			if (rootFile.isDirectory()) {
				File[] files = rootFile.listFiles(new SpaceFileFilter(
						includeName, excludeName));
				fileList.addAll(Arrays.asList(files));
				if (includeChildDir) {
					File[] allfiles = rootFile.listFiles();
					for (File file : allfiles) {
						if (file.isDirectory()) {
							fileList.addAll(listFiles(file.getAbsolutePath(),
									includeName, excludeName, includeChildDir));
						}
					}
				}
			}
		}
		return fileList;
	}

	/**
	 * @Title: getComposeFilename
	 * @Description: 格式为 ：file_versionname_resId 下载文件名命名格式
	 * @param filename
	 * @param versionName
	 * @param resId
	 * @return
	 * @throws
	 */
	public static String getComposeFilename(String filename,
			String versionName, String resId) {
		StringBuffer sb = new StringBuffer();
		if (filename != null && !filename.trim().equals("")) {
			sb.append(filename);
			sb.append((versionName == null || versionName.trim().equals("")) ? ""
					: FILE_SEPARATOR + versionName);
			sb.append((resId == null || resId.trim().equals("")) ? ""
					: FILE_SEPARATOR + resId);
			return sb.toString();
		}
		return null;
	}

	/**
	 * 功能说明: 文件过滤器
	 * 
	 * @Author 陈小冬
	 * @Date 2011-1-6
	 * @Version 1.0
	 */
	public static class SpaceFileFilter implements FileFilter {

		private String[] includeName, excludeName;

		public SpaceFileFilter(String[] includeName, String[] excludeName) {
			this.includeName = includeName;
			this.excludeName = excludeName;
		}

		@Override
		public boolean accept(File pathname) {
			if (pathname.isDirectory()) {
				return false;
			}
			String path = pathname.getName();
			if (includeName != null) {
				for (String include : includeName) {
					if (path.endsWith(include)) {
						return true;
					}
				}
				return false;
			}
			if (excludeName != null) {
				for (String exclude : excludeName) {
					if (path.endsWith(exclude)) {
						return false;
					}
				}
				return true;
			}
			return true;
		}
	}

	public static boolean isImage(File file) {
		String type = getFileType(file);
		if (type != null) {
			if (type.equals("jpg") || type.equals("png") || type.equals("gif")
					|| type.equals("bmp")) {
				return true;
			}
		}
		return false;
	}

	public static String getFileType(File file) {
		try {
			// 从SDCARD下读取一个文件
			FileInputStream inputStream = new FileInputStream(
					file.getAbsolutePath());
			byte[] buffer = new byte[2];
			// 文件类型代码
			String filecode = "";
			// 文件类型
			String fileType = "";
			// 通过读取出来的前两个字节来判断文件类型
			if (inputStream.read(buffer) != -1) {
				for (int i = 0; i < buffer.length; i++) {
					// 获取每个字节与0xFF进行与运算来获取高位，这个读取出来的数据不是出现负数
					// 并转换成字符串
					filecode += Integer.toString((buffer[i] & 0xFF));
				}
				// 把字符串再转换成Integer进行类型判断
				switch (Integer.parseInt(filecode)) {
				case 7790:
					fileType = "exe";
					break;
				case 7784:
					fileType = "midi";
					break;
				case 8297:
					fileType = "rar";
					break;
				case 8075:
					fileType = "zip";
					break;
				case 255216:
					fileType = "jpg";
					break;
				case 7173:
					fileType = "gif";
					break;
				case 6677:
					fileType = "bmp";
					break;
				case 13780:
					fileType = "png";
					break;
				default:
					fileType = "unknown type: " + filecode;
				}

			}
			return fileType;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}



	/**
	 * @Methods: getFileSize
	 * @Description: 获取文件夹的大小，包含子文件夹也可以
	 * @param f
	 *            File 实例
	 * @return 文件夹大小，单位：字节
	 * @throws Exception
	 * @throws
	 */
	public static long getFileSize(File f) {
		long size = 0;

		if (f.exists()) {
			File flist[] = f.listFiles();
			for (int i = 0; i < flist.length; i++) {
				if (flist[i].isDirectory()) {
					size = size + getFileSize(flist[i]);
				} else {
					size = size + flist[i].length();
				}
			}
		}
		return size;
	}

	/*
	 * 取得所有SD卡信息
	 */
	public static String getExternalStorageDirectory() {
		try {
			Runtime runtime = Runtime.getRuntime();
			Process proc = runtime.exec("mount");
			java.io.InputStream is = proc.getInputStream();
			java.io.InputStreamReader isr = new java.io.InputStreamReader(is);
			String line;
			String mount = "";
			java.io.BufferedReader br = new java.io.BufferedReader(isr);
			while ((line = br.readLine()) != null) {
				if (line.contains("secure"))
					continue;
				if (line.contains("asec"))
					continue;

				if (line.contains("fat")) {
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
						// 外置SD（TF）卡地址
						return mount.concat(columns[1]);
					}
				} else if (line.contains("fuse")) {
					// 内置SD卡地址
					String columns[] = line.split(" ");
					if (columns != null && columns.length > 1) {
//						mount = mount.concat(columns[1] + "\n");
					}
				}
			}
			return "";

		} catch (java.io.FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (java.io.IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	// 得到sdcard的 总大小
	public static long getTotalExternalMemorySize(String path) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			boolean isCatch = false;
			Log.d(TAG, "getTotalExternalMemorySize:"
					+ Environment.getExternalStorageDirectory()
							.getAbsolutePath());
			try {
				StatFs sf = new StatFs(path);
				long blockSize = sf.getBlockSize();
				long blockCount = sf.getBlockCount();
				return blockSize * blockCount;
			} catch (Exception e) {
				isCatch = true;
			}
			if (isCatch) {
				try {
					StatFs sf = new StatFs("/storage/sdcard1");
					long blockSize = sf.getBlockSize();
					long blockCount = sf.getBlockCount();
					return blockSize * blockCount;
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}

		return 0;
	}

	// 得到sdcard的 剩余大小
	public static long getAvailableExternalMemorySize(String path) {
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			Log.d(TAG, "path:"+path+" | "+Environment.getExternalStorageDirectory().getPath());
			boolean isCatch = false;
			try {
				StatFs sf = new StatFs(path);
				long blockSize = sf.getBlockSize();
				long blockAvail = sf.getAvailableBlocks();
				return blockSize * blockAvail;
			} catch (Exception e) {
				isCatch = true;
			}
			if (isCatch) {
				try {
					StatFs sf = new StatFs("/storage/sdcard1");
					long blockSize = sf.getBlockSize();
					long blockAvail = sf.getAvailableBlocks();
					return blockSize * blockAvail;
				} catch (Exception e) {
					// TODO: handle exception
				}

			}
		}

		return 0;
	}

}

