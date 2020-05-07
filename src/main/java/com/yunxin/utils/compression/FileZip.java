package com.yunxin.utils.compression;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Consumer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * FileZipUtils.zipFiles(Paths.get("./dir/example"),Paths.get("./dir/a.zip"));
 */
public class FileZip {

    /**
     * 压缩文件目录
     * @param source 源文件目录（单个文件和多层目录）
     * @param destit 目标目录
     */
    public static void zipFiles(Path source, Path destit) {

        ZipOutputStream zipOutputStream = null;
        OutputStream fileOutputStream = null;
        try {
            fileOutputStream = Files.newOutputStream(destit);
            zipOutputStream = new ZipOutputStream( fileOutputStream );

            ZipOutputStream finalZipOutputStream = zipOutputStream;

            Files.list(source).forEach(new Consumer<Path>() {
                @Override
                public void accept(Path path) {
                    System.out.println("reading path: "+path.getFileName());
                    if (Files.isDirectory(path)) {
                        System.out.println("dd: "+path);
                        directory(finalZipOutputStream, path, "" );
                    } else {
                        System.out.println("ff: "+path);
                        zipFile(finalZipOutputStream, path, "" );
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zipOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private static void zipFile(ZipOutputStream zipOutputStream, Path file, String parentFileName){
        InputStream in = null;

        try {
            ZipEntry zipEntry = null;
            if(parentFileName.isEmpty()){
                zipEntry = new ZipEntry( file.getFileName().toString() );
            }else{
                zipEntry = new ZipEntry( parentFileName+"/"+file.getFileName().toString() );
            }

            zipOutputStream.putNextEntry( zipEntry );
            in = Files.newInputStream(file);
            int len;
            byte [] buf = new byte[8*1024];
            while ((len = in.read(buf)) != -1){
                zipOutputStream.write(buf, 0, len);
            }
            zipOutputStream.closeEntry(  );
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try{
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归压缩目录结构
     * @param zipOutputStream
     * @param file
     * @param parentFileName
     */
    private static void directory(ZipOutputStream zipOutputStream,Path file,String parentFileName){
        final String[] parentFileNameTemp = {parentFileName};
        String dirEntryName = "";
        if(parentFileName.isEmpty()){
            dirEntryName = file.getFileName().toString();
        }else{
            dirEntryName = parentFileName+"/"+file.getFileName().toString();
        }
        try {
            zipOutputStream.putNextEntry(new ZipEntry(dirEntryName+"/"));
            zipOutputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("dir: "+dirEntryName);
        try {
            String finalDirEntryName = dirEntryName;
            Files.list(file).forEach(new Consumer<Path>() {
                @Override
                public void accept(Path fileTemp) {
                    if(fileTemp.getFileName().toString().equals(".DS_Store")){
                        return;
                    }

                    parentFileNameTemp[0] = finalDirEntryName;
                    if(Files.isDirectory(fileTemp)){

                        directory(zipOutputStream,fileTemp, parentFileNameTemp[0]);
                    }else{
                        zipFile(zipOutputStream,fileTemp, parentFileNameTemp[0]);
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
