/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package figoo.fileManager;

import com.google.gdata.client.docs.DocsService;
import com.google.gdata.client.photos.PicasawebService;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.MediaContent;
import com.google.gdata.data.media.MediaSource;
import com.google.gdata.data.photos.AlbumFeed;
import com.google.gdata.data.photos.PhotoEntry;
import com.google.gdata.util.ServiceException;
import com.mortennobel.imagescaling.ResampleOp;
import figoo.ErrorDialog;
import figoo.FigooView;
import figoo.classes.FileInfo;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.MimetypesFileTypeMap;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

/**
 *
 * @author Lada Riha
 */
public class FileManager {

    private static int height;
    private static int width;
    private static int folders = 0;
    private static int files = 0;

    /**
     * Renames file
     * @param name old name (absolute path)
     * @param newname new name
     * @throws Exception
     */
    public static void rename(String name, String newname) throws Exception {
        File old = new File(name);
        String sub = name.substring(0, name.lastIndexOf(System.getProperty("file.separator")) + 1);
        newname = sub + newname;
        File newFile = new File(newname);
        boolean test = old.renameTo(newFile);
        if (!test) {
            throw new Exception("Unable to rename file or folder");
        }
    }

    /**
     * Makes new directory
     * @param path path where to make new directory
     * @param name name of new directory
     * @throws IOException
     * @throws Exception
     */
    public static void makeDir(String path, String name) throws IOException, Exception {
        File f = new File(path + System.getProperty("file.separator") + name);
        boolean created = f.mkdirs();
        if (!created) {
            throw new Exception("Unable to create new dir");
        }
    }

    /**
     * Gets file info
     * @param path file to get info about
     * @param fw
     * @return
     */
    public static FileInfo getFileInfo(String path, FigooView fw) {
        folders = 0;
        files = 0;

        FileInfo fi = new FileInfo();
        fi.setPath(path);
        File f = new File(path);

        fi.setName(f.getName());
        long modi = f.lastModified();
        Date d = new Date(modi);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        String month = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
        String day = c.get(Calendar.DAY_OF_MONTH) + "";
        String year = c.get(Calendar.YEAR) + "";
        String hour = c.get(Calendar.HOUR_OF_DAY) + "";
        String minute = c.get(Calendar.MINUTE) + "";
        String date = day + " " + month + " " + year + ", " + hour + ":" + minute;
        fi.setLastModif(date);
        if (f.isDirectory()) {
            fi.setIsDir(true);
            fi.setOpenWith("");
            long size = getFileSize(f);
            fi.setHash("Not available for folders");
            String mb = (float) ((float) size / (float) 1000000) + "000";
            mb = mb.substring(0, mb.indexOf(".") + 3);
            fi.setSize(size / 1000 + "  kB (" + mb + " MB) in " + files + " files and " + folders + " folders");
        } else {
            fi.setHash(getHashFromFile(f));
            String mb = (float) ((float) f.length() / (float) 1000000) + "000";
            mb = mb.substring(0, mb.indexOf(".") + 3);
            fi.setSize(f.length() / 1000 + "  kB (" + mb + " MB)");
            fi.setIsDir(false);
        }
        javax.swing.JFileChooser fc = new javax.swing.JFileChooser();
        String filetype = fc.getTypeDescription(f);
        fi.setIcon(fw.getIcon(f));
        fi.setCanRead(f.canRead());
        fi.setCanWrite(f.canWrite());
        fi.setCanExecute(f.canExecute());

        String ext = "";
        try {
            ext = " (*" + f.getName().substring(f.getName().lastIndexOf(".")) + ")";
        } catch (Exception s) {
        }
        fi.setType(filetype + ext);
        return fi;
    }

    /**
     * Returns file's size in bytes
     * @param folder
     * @return
     */
    public static long getFileSize(File folder) {
        folders++;
        long foldersize = 0;
        File[] fl = folder.listFiles();
        if (fl != null) {
            for (int i = 0; i < fl.length; i++) {
                if (fl[i].isDirectory()) {
                    foldersize += getFileSize(fl[i]);
                } else {
                    files++;
                    foldersize += fl[i].length();
                }
            }
        }
        return foldersize;
    }

    /**
     * Gets file's MD5 hash
     * @param file
     * @return
     */
    private static String getHashFromFile(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            FileInputStream fis = new FileInputStream(file);
            if (file.length() <= Integer.MAX_VALUE) {
                int velikost = (int) file.length();
                byte[] pole = new byte[velikost];
                byte[] pole2;
                fis.read(pole);
                digest.update(pole);
                pole2 = digest.digest();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < pole2.length; i++) {
                    if (Integer.toHexString(0xFF & pole2[i]).length() == 1) {
                        buffer.append("0" + Integer.toHexString(0xFF & pole2[i]));
                    } else {
                        buffer.append(Integer.toHexString(0xFF & pole2[i]));
                    }
                }
                return buffer.toString();
            }
        } catch (Exception a) {
            a.printStackTrace();
            ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), true, "Error getting hash", a.getMessage());
            ed.setVisible(true);
        }
        return "";
    }

    /**
     * Downloads single photo from URL
     * @param urlAddress URL address
     * @param photoName name of photo
     * @param dir directory where to download
     * @param size which size download (on error downloads original size)
     */
    public static void downloadFromPicasaURL(String urlAddress, String photoName, String dir, String size) {
        InputStream is = null;
        String tmp = urlAddress;
        OutputStream outStream = null;

        try {

            String end = urlAddress.substring(urlAddress.lastIndexOf("/"));
            urlAddress = urlAddress.substring(0, urlAddress.lastIndexOf("/"));
            urlAddress = urlAddress.substring(0, urlAddress.lastIndexOf("/") + 1) + size + end;
            URL url = new URL(urlAddress);
            URLConnection con = url.openConnection();
            is = con.getInputStream();
            outStream = new BufferedOutputStream(new FileOutputStream(dir + System.getProperty("file.separator") + photoName));
            byte[] buf = new byte[10240];
            int byteRead, byteWritten = 0;
            while ((byteRead = is.read(buf)) != -1) {
                outStream.write(buf, 0, byteRead);
                byteWritten += byteRead;
            }
        } catch (Exception ex) {// try original url

            try {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                URL url = new URL(tmp);
                URLConnection con = url.openConnection();
                is = con.getInputStream();
                outStream = new BufferedOutputStream(new FileOutputStream(dir + System.getProperty("file.separator") + photoName));

                byte[] buf = new byte[10240];
                int byteRead, byteWritten = 0;
                while ((byteRead = is.read(buf)) != -1) {
                    outStream.write(buf, 0, byteRead);
                    byteWritten += byteRead;
                }

            } catch (Exception a) {
                a.printStackTrace();
                ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), false, "downloadFromPicasaURL error", a.getMessage());
                ed.setVisible(true);
            }
        } finally {
            try {
                is.close();
                outStream.close();
            } catch (IOException e) {
                e.printStackTrace();
                ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), false, "downloadFromPicasaURL error", e.getMessage());
                ed.setVisible(true);
            }
        }
    }

    /**
     * Downloads low quality photo to temporary directory, file is deleted on exit. If it fails to download low quality, it tries to download original photo
     * @param urlAddress photo's url address
     * @param photoName name of photo
     * @return
     */
    public static File downloadTmpFromPicasaURL(String urlAddress, String photoName) {
        InputStream is = null;
        String tmp = urlAddress;
        String size = "s640";
        OutputStream outStream = null;
        File tmpFile = null;
        try {
            String end = urlAddress.substring(urlAddress.lastIndexOf("/"));
            urlAddress = urlAddress.substring(0, urlAddress.lastIndexOf("/"));
            urlAddress = urlAddress.substring(0, urlAddress.lastIndexOf("/") + 1) + size + end;
            URL url = new URL(urlAddress);
            URLConnection con = url.openConnection();
            is = con.getInputStream();
            tmpFile = File.createTempFile("tmpPicasa", photoName.substring(photoName.lastIndexOf(".")));
            tmpFile.deleteOnExit();
            outStream = new BufferedOutputStream(new FileOutputStream(tmpFile));
            byte[] buf = new byte[10240];
            int byteRead, byteWritten = 0;
            while ((byteRead = is.read(buf)) != -1) {
                outStream.write(buf, 0, byteRead);
                byteWritten += byteRead;
            }
        } catch (Exception ex) {// try original url

            try {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
                URL url = new URL(tmp);
                URLConnection con = url.openConnection();
                is = con.getInputStream();
                tmpFile = File.createTempFile("tmpPicasa", photoName.substring(photoName.lastIndexOf(".")));
                tmpFile.deleteOnExit();
                outStream = new BufferedOutputStream(new FileOutputStream(tmpFile));
                byte[] buf = new byte[10240];
                int byteRead, byteWritten = 0;
                while ((byteRead = is.read(buf)) != -1) {
                    outStream.write(buf, 0, byteRead);
                    byteWritten += byteRead;
                }
            } catch (Exception a) {
                a.printStackTrace();
                ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), false, "downloadFromPicasaURL error", a.getMessage());
                ed.setVisible(true);
            }
        } finally {
            try {
                is.close();
                outStream.close();

            } catch (IOException e) {
                ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), false, "downloadFromPicasaURL error", e.getMessage());
                ed.setVisible(true);
                e.printStackTrace();
            }
        }
        return tmpFile;
    }

    /**
     *  Downloads file from Picasa
     * @param album album to download
     * @param id photo's id
     * @param picasa
     * @param username
     * @param to target directory
     * @param size selected size to download
     * @throws MalformedURLException
     * @throws IOException
     * @throws ServiceException
     */
    public static void downloadFileFromPicasa(AlbumFeed album, String id, PicasawebService picasa, String username, String to, String size) throws MalformedURLException, IOException, ServiceException {

        List<PhotoEntry> photos = album.getPhotoEntries();
        PhotoEntry photo;
        String url;
        String name;
        for (int i = 0; i < photos.size(); i++) {
            photo = photos.get(i);
            if (photo.getId().equalsIgnoreCase(id)) {
                url = (photo.getMediaThumbnails().get(0).getUrl());
                name = photo.getTitle().getPlainText();
                downloadFromPicasaURL(url, name, to, size);
            }
        }
    }

    /**
     * Returns list of all Picasa supported files in selected directory, so far works only for photos, not videos
     * @param dir selected directory to be searched
     * @return
     */
    public static ArrayList<File> getPicasaSupportedFiles(String dir) {
        File folder = new File(dir);

        String[] files = folder.list();
        File f;
        ArrayList<File> filesToUpload = new ArrayList<File>();
        for (int i = 0; i < files.length; i++) {
            f = new File(dir + System.getProperty("file.separator") + files[i]);
            String mime = new MimetypesFileTypeMap().getContentType(f);
            mime = mime.trim();
            if (mime.endsWith("bmp") || mime.endsWith("gif") || mime.endsWith("jpeg") || mime.endsWith("png")) {
                filesToUpload.add(f);
            }
        }
        return filesToUpload;
    }

    /**
     * Returns scaled image
     * @param file image to be scaled
     * @param size desired size
     * @return
     * @throws IOException
     * @throws Exception
     */
    public synchronized static File getScaledImage(File file, String size) throws IOException, Exception {

        int newSize = Integer.valueOf(size).intValue();
        if (isResizeNeccessery(file, newSize)) {
            int newHeight;
            int newWidth;
            newWidth = newSize;
            float change = change = (float) ((float) width / (float) newWidth);
            newHeight = (int) (height / change);
            if (height >= width) {
                newHeight = newSize;
                change = (float) ((float) height / (float) newHeight);
                newWidth = (int) (width / change);
            }
            BufferedImage originalImage = ImageIO.read(file);
            ResampleOp resampleOp = new ResampleOp(newWidth, newHeight);
            BufferedImage resizedImage = resampleOp.filter(originalImage, null);
            String name = (file.getCanonicalFile().getName());
            name = name.substring(0, name.lastIndexOf("."));
            File newImage = new File(System.getProperty("java.io.tmpdir") + System.getProperty("file.separator") + file.getName());
            newImage.deleteOnExit();
            ImageIO.write(resizedImage, file.getName().substring(file.getName().lastIndexOf(".") + 1), newImage);
            return newImage;
        }
        return file;
    }

    private static boolean isResizeNeccessery(File newImage, int size) throws IOException, Exception {

        ImageInputStream imageStream = ImageIO.createImageInputStream(newImage);
        Iterator<ImageReader> readers = ImageIO.getImageReaders(imageStream);
        ImageReader reader = null;
        if (!readers.hasNext()) {
            throw new Exception("Cannot read image file");
        } else {
            reader = readers.next();
        }
        reader.setInput(imageStream, true, true);

        width = reader.getWidth(0);
        height = reader.getHeight(0);
        reader.dispose();
        imageStream.close();

        if (width == size) {
            return false;
        }
        return true;
    }

    /**
     * Downloads file from given url address
     * @param exportUrl
     * @param filepath target folder
     * @param client
     * @throws IOException
     * @throws MalformedURLException
     * @throws ServiceException
     */
    public static void downloadFile(String exportUrl, String filepath, DocsService client) throws IOException, MalformedURLException, ServiceException {
        MediaContent mc = new MediaContent();
        mc.setUri(exportUrl);

        MediaSource ms = client.getMedia(mc);

        InputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = ms.getInputStream();
            outStream = new FileOutputStream(filepath);

            int c;
            while ((c = inStream.read()) != -1) {
                outStream.write(c);
            }
        } catch (Exception ex) {
                 ex.printStackTrace();
                ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), true, "uploadFileFolder", ex.getMessage());
                ed.setVisible(true);
        } finally {
            if (inStream != null) {
                inStream.close();
            }
            if (outStream != null) {
                outStream.flush();
                outStream.close();
            }
        }
    }

    /**
     * Downloads file from given url address
     * @param exportUrl
     * @param filepath target folder
     * @param client
     * @param spread
     * @throws IOException
     * @throws MalformedURLException
     * @throws ServiceException
     */
    public static void downloadFile(String exportUrl, String filepath, DocsService client, SpreadsheetService spread) throws IOException, MalformedURLException, ServiceException {
       MediaContent mc = new MediaContent();
        mc.setUri(exportUrl);

        MediaSource ms = client.getMedia(mc);

        InputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = ms.getInputStream();
            outStream = new FileOutputStream(filepath);

            int c;
            while ((c = inStream.read()) != -1) {
                outStream.write(c);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorDialog ed = new ErrorDialog(new javax.swing.JFrame(), true, "uploadFileFolder", ex.getMessage());
            ed.setVisible(true);
        } finally {
            if (inStream != null) {
                inStream.close();
            }
            if (outStream != null) {
                outStream.flush();
                outStream.close();
            }
        }
    }
}


