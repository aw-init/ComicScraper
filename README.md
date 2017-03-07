# ComicScraper
Simple framework and implementation for scraping comics from certain websites and compiling them as .cbz files.

There is currently no command line interface. Instead, simply create a tasks.txt, and enter the following commands on their own line.
Commands will be executed one at a time from the top down. Most tasks will replace themselves with a number of sub-tasks at the top of the stack.
Note: comments below are for demonstration purposes. #comments are not legal in the actual tasks.txt

    # download the volume at mangareader.net/name-of-manga to the directory /location/of/volume/
    VolumeTask /location/of/volume/ name-of-manga
    
    # convert the volume at /location/of/volume/ to a cbz zip file saved at /location/of/cbz/file
    ZipTask /location/of/volume/ /location/of/cbz/file
    
    # display a single word. for debug purposes.
    EchoTask WordToPrint
