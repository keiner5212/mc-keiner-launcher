package minecraft.client.GUI.Background.download;

import java.io.File;

import minecraft.client.api.common.mc.MinecraftInstance;

/**
 * LibraryProvider is responsible for managing libraries folder inside a minecraft instance
 */
final class LibraryProvider {
    private final File libraryFolder;

    LibraryProvider(MinecraftInstance mc){
        this.libraryFolder = new File(mc.getLocation(), "libraries");
    }

    /**
     *
     * @param library the library whose file you want to retrieve
     * @return Location of JAR of this library
     */
    File getLibraryFile(Library library){
        String path = library.getPath().replace('/', File.separatorChar);
        return new File(libraryFolder, path);
    }

    /**
     *
     * @param library The library we want to check
     * @return True if the specified library is installed, otherwise false
     */
    boolean isInstalled(Library library){
        return getLibraryFile(library).exists();
    }

    File getLibraryFolder(){ return libraryFolder; }


}
