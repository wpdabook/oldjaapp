package com.a21zhewang.constructionapp.download;

import android.text.TextUtils;
import android.util.Log;

import com.a21zhewang.constructionapp.MyAppCon;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/6/1.
 */

public class DownLoadFileUtils {

    public final static int DEFAULT_FILE_OPERATE_MODE = 0;

    public final static int IGNORE_NOT_RECREATE_MODE = 1;

    public final static int IGNORE_AND_RECREATE_MODE = 2;

    public final static int NOT_IGNORE_RECREATE_MODE = 3;

    private final static boolean DEFAULT_IGNORE_STYLE = false;

    private final static boolean DEFAULT_AUTO_CREATE_DIRECTORY = true;

    /** 分隔符. */
    public final static String FILE_EXTENSION_SEPARATOR = ".";

    // ============== create file and dirctory==================
    /**
     * @param file
     * @throws IOException
     */
    public static void createFile(File file, int mode) throws IOException
    {

        if (file == null || StringUtils.isEmpty(file.getAbsolutePath()) || file
                .isDirectory())
        {
            return;
        }
        if (mode == IGNORE_NOT_RECREATE_MODE || mode == IGNORE_AND_RECREATE_MODE)
        {
            file = new File(StorageUtils.getStorageFile(), file.getAbsolutePath());
        }
        if (mode == IGNORE_AND_RECREATE_MODE)
        {
            deleteFile(file);
        }
        file.createNewFile();
    }

    /**
     * @param filePath
     * @param mode
     * @throws IOException
     */
    public static void createFile(String filePath, int mode) throws IOException
    {

        createFile(new File(filePath), mode);
    }

    /**
     * @param filePath
     * @throws IOException
     */
    public static void createFile(File filePath) throws IOException
    {

        createFile(filePath, DEFAULT_FILE_OPERATE_MODE);
    }

    /**
     * @param filePath
     * @throws IOException
     */
    public static void createFile(String filePath) throws IOException
    {

        createFile(new File(filePath));
    }

    /**
     * To create a folder or more.
     *
     * @param folder
     * @param mode
     */
    public static void createFolder(File folder, int mode)
    {

        if (folder == null || StringUtils.isEmpty(folder.getAbsolutePath()))
        {
            return;
        }
        if (folder.isFile())
        {
            return;
        }
        if (mode == IGNORE_NOT_RECREATE_MODE || mode == IGNORE_AND_RECREATE_MODE)
        {
            folder = new File(StorageUtils.getStorageFile(), folder.getAbsolutePath());
        }
        if (mode == IGNORE_AND_RECREATE_MODE)
        {
            deleteFolder(folder);
        }
        folder.mkdirs();

    }

    /**
     * @param folder
     * @param mode
     */
    public static void createFolder(String folder, int mode)
    {

        createFolder(new File(folder), mode);
    }

    /**
     * @param folder
     */
    public static void createFolder(File folder)
    {

        createFolder(folder, DEFAULT_FILE_OPERATE_MODE);
    }

    /**
     * @param folder
     */
    public static void createFolder(String folder)
    {

        createFolder(new File(folder));
    }

    // ============== delete file and dirctory==================
    /**
     * delete a file.
     *
     * @param file
     */
    public static void deleteFile(File file)
    {

        if (file == null || StringUtils.isEmpty(file.getAbsolutePath()))
        {
            return;
        }
        if (file.exists())
        {
            if (file.isFile())
            {
                file.delete();
            }
        }
    }

    /**
     * @param filePath
     */
    public static void deleteFile(String filePath)
    {

        if (!StringUtils.isEmpty(filePath))
        {
            deleteFile(new File(filePath));
        }
    }

    /**
     * delete folder.
     *
     * @param folder
     */
    public static void deleteFolder(File folder)
    {

        if (folder == null || StringUtils.isEmpty(folder.getAbsolutePath()))
        {
            return;
        }
        if (folder.exists())
        {
            if (folder.isDirectory())
            {
                File[] files = folder.listFiles();
                if (files != null)
                {
                    for (File file : files)
                    {
                        deleteFolder(file);
                    }
                }
                folder.delete();
            } else
            {
                deleteFile(folder);
            }
        }
    }

    /**
     * @param folderPath
     */
    public static void deleteFolder(String folderPath)
    {

        if (!StringUtils.isEmpty(folderPath))
        {
            deleteFolder(new File(folderPath));
        }
    }

    // ===========find file in order to extendsions at the end=================
    /**
     * In order to "end" At the end.
     *
     * @return
     */
    public static List<File> getAllWithEnd(File file, boolean ignore, String... extensions)
    {

        if (StringUtils.isEmpty(file.getAbsolutePath()))
        {
            return null;
        }
        for (String extension : extensions)
        {
            if (StringUtils.isEmpty(extension))
            {
                return null;
            }
        }
        if (ignore)
        {
            file = new File(StorageUtils.getStorageFile(), file.getAbsolutePath());
        }
        if ((!file.exists()) && file.isDirectory())
        {
            return null;
        }
        List<File> files = new ArrayList<File>();
        fileFilter(file, files, extensions);
        return files;

    }

    /**
     * @param path
     * @param extensions
     * @param ignore
     * @return
     */
    public static List<File> getAllWithEnd(String path, boolean ignore, String... extensions)
    {

        return getAllWithEnd(new File(path), ignore, extensions);
    }

    /**
     * @param file
     * @param extensions
     * @return
     */
    public static List<File> getAllWithEnd(File file, String... extensions)
    {

        return getAllWithEnd(file, DEFAULT_IGNORE_STYLE, extensions);
    }

    /**
     * @param file
     * @param extensions
     * @return
     */
    public static List<File> getAllWithEnd(String file, String... extensions)
    {

        return getAllWithEnd(new File(file), DEFAULT_IGNORE_STYLE, extensions);
    }

    /**
     * filter the file.
     *
     * @param file
     * @param extensions
     * @param files
     */
    public static void fileFilter(File file, List<File> files, String... extensions)
    {

        if (!file.isDirectory())
        {
            return;
        }
        File[] allFiles = file.listFiles();
        File[] allExtensionFiles = file.listFiles(new MyFileFilter(extensions));
        if (allExtensionFiles != null)
        {
            for (File single : allExtensionFiles)
            {
                files.add(single);
            }
        }
        if (allFiles != null)
        {
            for (File single : allFiles)
            {
                if (single.isDirectory())
                {
                    fileFilter(single, files, extensions);
                }
            }
        }
    }

    // ===========copy assert to a storage=================
    /**
     * The assert directory, copy files to a storage card.
     *
     * @param strAssetsFilePath
     * @param strDesFilePath
     * @return
     */
    public boolean assetsCopyData(String strAssetsFilePath, String strDesFilePath)
    {

        boolean bIsSuc = true;
        InputStream inputStream = null;
        OutputStream outputStream = null;

        File file = new File(strDesFilePath);
        if (!file.exists())
        {
            try
            {
                file.createNewFile();
                Runtime.getRuntime().exec("chmod 766 " + file);
            } catch (IOException e)
            {
                bIsSuc = false;
            }

        } else
        {// 存在
            return true;
        }

        try
        {
            inputStream = MyAppCon.appcontext.getAssets()
                    .open(strAssetsFilePath);
            outputStream = new FileOutputStream(file);

            int nLen = 0;

            byte[] buff = new byte[1024 * 1];
            while ((nLen = inputStream.read(buff)) > 0)
            {
                outputStream.write(buff, 0, nLen);
            }

            // 完成
        } catch (IOException e)
        {
            bIsSuc = false;
        } finally
        {
            try
            {
                if (outputStream != null)
                {
                    outputStream.close();
                }

                if (inputStream != null)
                {
                    inputStream.close();
                }
            } catch (IOException e)
            {
                bIsSuc = false;
            }

        }

        return bIsSuc;
    }

    // ===========copy single file.=================
    /**
     * To copy single file.
     *
     * @param src
     * @param dst
     * @return
     * @throws IOException
     */
    public static boolean copyFile(File src, File dst) throws IOException
    {

        if ((!src.exists()) || src.isDirectory() || dst.isDirectory())
        {
            return false;
        }
        if (!dst.exists())
        {
            dst.getParentFile().mkdirs();
            dst.createNewFile();
            // return false;
        }
        FileInputStream inputStream = null;
        inputStream = new FileInputStream(src);
        return copyFile(inputStream, dst);
    }

    /**
     * 从输入流复制文件
     *
     * @param inputStream
     * @param dst
     * @return
     * @throws IOException
     */
    public static boolean copyFile(InputStream inputStream, File dst) throws IOException
    {

        FileOutputStream outputStream = null;
        outputStream = new FileOutputStream(dst);
        int readLen = 0;
        byte[] buf = new byte[1024];
        while ((readLen = inputStream.read(buf)) != -1)
        {
            outputStream.write(buf, 0, readLen);
        }
        outputStream.flush();
        inputStream.close();
        outputStream.close();
        return true;
    }

    /**
     * @param src
     * @param dst
     * @return
     * @throws IOException
     */
    public static boolean copyFile(String src, String dst) throws IOException
    {

        return copyFile(new File(src), new File(dst));
    }

    // ===========copy folder to storage=================
    /**
     * To copy the folder.
     *
     * @param srcDir
     * @param destDir
     * @param auto
     * @return
     * @throws IOException
     */
    public static boolean copyFolder(File srcDir, File destDir, boolean auto) throws IOException
    {

        if ((!srcDir.exists()))
        {
            return false;
        }
        if (srcDir.isFile() || destDir.isFile())
            return false;// 判断是否是目录
        if (!destDir.exists())
        {
            if (auto)
            {
                destDir.mkdirs();
            } else
            {
                return false;// 判断目标目录是否存在
            }
        }
        File[] srcFiles = srcDir.listFiles();
        int len = srcFiles.length;
        for (int i = 0; i < len; i++)
        {
            if (srcFiles[i].isFile())
            {
                // 获得目标文件
                File destFile = new File(destDir.getPath() + "//" + srcFiles[i].getName());
                copyFile(srcFiles[i], destFile);
            } else if (srcFiles[i].isDirectory())
            {
                File theDestDir = new File(destDir.getPath() + "//" + srcFiles[i].getName());
                copyFolder(srcFiles[i], theDestDir, auto);
            }
        }
        return true;
    }

    /**
     * @param srcDir
     * @param desDir
     * @param auto
     * @return
     * @throws IOException
     */
    public static boolean copyFolder(String srcDir, String desDir, boolean auto) throws IOException
    {

        return copyFolder(new File(srcDir), new File(desDir), auto);
    }

    /**
     * @param srcDir
     * @param desDir
     * @return
     * @throws IOException
     */
    public static boolean copyFolder(File srcDir, File desDir) throws IOException
    {

        return copyFolder(srcDir, desDir, DEFAULT_AUTO_CREATE_DIRECTORY);
    }

    /**
     * @param srcDir
     * @param desDir
     * @return
     * @throws IOException
     */
    public static boolean copyFolder(String srcDir, String desDir) throws IOException
    {

        return copyFolder(srcDir, desDir, DEFAULT_AUTO_CREATE_DIRECTORY);
    }

    // ===========move single file to storage=================
    /**
     * To move the single file.
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean moveFile(File src, File dst, boolean isDeleteOld)
    {

        boolean isCopy = false;
        isCopy = src.renameTo(dst);
        if (!isCopy)
        {
            return false;
        }
        if (isDeleteOld)
        {
            deleteFile(src);
        }
        return true;
    }

    /**
     * @param src
     * @param dst
     * @return
     */
    public static boolean moveFile(String src, String dst, boolean isDeleteOld)
    {

        return moveFile(new File(src), new File(dst), isDeleteOld);
    }

    // ===========move folder to storage=================
    /**
     * To move the folder.
     *
     * @param srcDir
     * @param destDir
     * @param auto
     * @return
     */
    public static boolean moveFolder(File srcDir, File destDir, boolean auto, boolean isDeleteOld)
    {

        if (srcDir.isFile() || destDir.isFile())
        {
            return false;
        }
        if (!srcDir.exists())
        {
            return false;
        }
        if (!destDir.exists())
        {
            if (auto)
            {
                destDir.mkdirs();
            } else
            {
                return false;
            }
        }
        File[] srcDirFiles = srcDir.listFiles();
        int len = srcDirFiles.length;
        if (len <= 0)
        {
            srcDir.delete();
        }
        for (int i = 0; i < len; i++)
        {
            if (srcDirFiles[i].isFile())
            {
                File oneDestFile = new File(destDir.getPath() + "//" + srcDirFiles[i].getName());
                moveFile(srcDirFiles[i], oneDestFile, isDeleteOld);
            } else if (srcDirFiles[i].isDirectory())
            {
                File oneDestFile = new File(destDir.getPath() + "//" + srcDirFiles[i].getName());
                moveFolder(srcDirFiles[i], oneDestFile, auto, isDeleteOld);
                srcDirFiles[i].renameTo(oneDestFile);
                if (isDeleteOld)
                {
                    deleteFolder(srcDirFiles[i]);
                }
            }

        }
        if (srcDir.exists())
        {
            srcDir.renameTo(destDir);
            if (isDeleteOld)
            {
                srcDir.delete();
            }
        }
        return true;
    }

    /**
     * @param src
     * @param dst
     * @param auto
     * @return
     */
    public static boolean moveFolder(String src, String dst, boolean auto, boolean isDeleteOld)
    {

        return moveFolder(new File(src), new File(dst), isDeleteOld);
    }

    /**
     * @param src
     * @param dst
     * @return
     */
    public static boolean moveFolder(File src, File dst, boolean isDeleteOld)
    {

        return moveFolder(src, dst, DEFAULT_AUTO_CREATE_DIRECTORY, isDeleteOld);
    }

    /**
     * @param src
     * @param dst
     * @return
     */
    public static boolean moveFolder(String src, String dst, boolean isDeleteOld)
    {

        return moveFolder(new File(src), new File(dst), DEFAULT_AUTO_CREATE_DIRECTORY, isDeleteOld);
    }

    // ===========get private directory=================
    /**
     * To get private directory.
     *
     * @return
     */
    public static File getPrivateDir()
    {

        return MyAppCon.appcontext.getFilesDir();
    }


    // 格式化url
    public static String formatUrl(String url)
    {

        String[] string = url.split("\\?");
        if (string.length > 0)
        {
            return string[0];
        }
        return url;
    }

    /**
     * 获取文件大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFileSize(File file) throws Exception
    {

        long size = 0;
        if (file.exists())
        {
            size = file.length();
        } else
        {
            file.createNewFile();
            Log.e("获取文件大小", "文件不存在!");
        }
        return size;
    }

    /**
     * 获取文件夹大小
     *
     * @param f
     * @return
     * @throws Exception
     */
    public static long getFileSizes(File f) throws Exception
    {

        long size = 0;
        File flist[] = f.listFiles();
        if (flist == null)
        {
            return 0;
        }
        for (int i = 0; i < flist.length; i++)
        {
            if (flist[i].isDirectory())
            {
                size = size + getFileSizes(flist[i]);
            } else
            {
                size = size + getFileSize(flist[i]);
            }
        }
        return size;
    }

    /**
     * 检测是否有SD卡权限
     * @return 是否有SD卡权限
     */
    public static boolean hasSdcard()
    {
        if (android.os.Environment.getExternalStorageState()
                .equals(android.os.Environment.MEDIA_MOUNTED))
        {
            return true;
        }
        return false;
    }

    /**
     * 删除指定目录中特定的文件
     * @param dir
     * @param filter
     */
    public static void delete(String dir, FilenameFilter filter) {
        if (StringUtils.isEmpty(dir))
            return;
        File file = new File(dir);
        if (!file.exists())
            return;
        if (file.isFile())
            file.delete();
        if (!file.isDirectory())
            return;

        File[] lists = null;
        if (filter != null)
            lists = file.listFiles(filter);
        else
            lists = file.listFiles();

        if (lists == null)
            return;
        for (File f : lists) {
            if (f.isFile()) {
                f.delete();
            }
        }
    }

    /**
     * 获得不带扩展名的文件名称
     * @param filePath 文件路径
     * @return
     */
    public static String getFileNameWithoutExtension(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }
        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1 ? filePath : filePath.substring(0,
                    extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi) : filePath.substring(filePosi + 1));
    }

}
