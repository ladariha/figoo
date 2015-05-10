# Figoo #
Figoo is a simple file manager that supports basic operations with files (copy/move/delete/rename/open/properties) and adds support for online services. So far only Picasa and Google Docs are supported, but possibly Dropbox is on todo list.

## Supported functions for file system ##
  * copy/move/delete/rename/show properties
  * open files with default software
  * Browse all partitions (usb flashdisc, memory cards...)
  * Making ZIP archives with custom level of compression
  * Checking hash using clipboard or manually (in file properties)
  * Detailed info about files (select Properties -> Details)
    * ID3 tags - support for mp3, m4a, flac and ogg
    * EXIF information - support for jpg, raw and tiff
  * Thumbmails
    * right click on pdf/jpg/png/gif file, select properties and card Thumbmail
    * use middle/wheel mouse button to show thumbmail (for pdf, jpg, png, gif) or to show properties (other filetypes and directories)
  * Copy structure of files and directories to clipboard/file (with custom depth of search)
  * Split/Combine files (Total Commander compatible)
  * Batch rename & copy
  * Creating M3U playlist from multiple directories

## Supported functions for Picasa ##
  * list albums and photos
  * view and modify album properties
  * view and modify photo properties (EXIF information only readable)
  * rename album
  * upload new album with size selection (uploading videos is not working)
  * download multiple albums at once or just selected photos (with size selection)
  * delete album/photos
  * "instant" photo open - opening photo with width of 640px by double-click
  * Copy structure of files and directories to clipboard/file (with custom depth of search)

## Supported functions for Google Documents ##
  * list folders and files
  * download files/folders
  * delete files/folders (moving trash supported)
  * rename files/folders
  * Export documents to: txt, odt, pdf, html, rtf, doc, zip
  * Export spreadsheets to: xls, csv, pdf, ods, tsv, html
  * Export presentations to: swf, pdf, png, ppt
  * Batch export in 3 modes:
    * doc|xls|ppt
    * pdf|pdf|pdf
    * odt|ods|ppt
  * limited upload (due to Google API) - all files are converted to Google Documents
  * Copy structure of files and directories to clipboard/file (with custom depth of search)



![http://lh3.ggpht.com/_dBMhnDbZcy8/THKVbqfqniI/AAAAAAAAS3k/kEKjmvJOreA/s800/mainM.jpg](http://lh3.ggpht.com/_dBMhnDbZcy8/THKVbqfqniI/AAAAAAAAS3k/kEKjmvJOreA/s800/mainM.jpg)


### External libraries ###
Project requires some external libraries. They are all in main zip file.

Links to some used libraries:
  1. Image Scaling: http://code.google.com/p/java-image-scaling/
  1. PDF Renderer: https://pdf-renderer.dev.java.net/
  1. JCalendar: http://www.toedter.com/en/jcalendar/index.html
  1. Metadata Extractor: http://www.drewnoakes.com/drewnoakes.com/code/exif
  1. Jaudiotagger: http://www.jthink.net/jaudiotagger/

Other libraries:
  1. Google Documents List Data API, Picasa Web Albums Data API ...

### Other ###
  1. [See Figoo at Softpedia.com](http://www.softpedia.com/get/System/File-Management/Figoo.shtml)