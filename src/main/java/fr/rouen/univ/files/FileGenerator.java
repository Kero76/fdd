package fr.rouen.univ.files;

import fr.rouen.univ.models.Mesh;
import fr.rouen.univ.models.PubmedArticle;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class FileGenerator {

    /**
     * Logger to check process of each methods.
     */
    private static Logger logger = Logger.getLogger(FileGenerator.class.getName());

    /**
     * Create file Unindexed.
     *
     * @param path
     *  Path of the file.
     * @param extension
     *  Extension for the file.
     * @param articles
     *  List of PubMed articles.
     * @throws IOException
     */
    public static void fileUnindexed(String path, String extension, List<PubmedArticle> articles) throws IOException {
        logger.info("Create Unindexed files");
        Writer writer = null;
        StringBuilder stringBuilder = null;

        for (PubmedArticle article : articles) {
            try {
                writer = FileGenerator.instanciateWriter(path, article.getPmid(), extension);
                stringBuilder = new StringBuilder();
                stringBuilder.append("T. ");
                stringBuilder.append(article.getTitleText());
                stringBuilder.append(System.getProperty("line.separator"));
                stringBuilder.append("A. ");
                stringBuilder.append(article.getAbstractText());
                logger.info("Writer write content on file");
                writer.write(stringBuilder.toString());
                logger.info("File " + path + article.getPmid() + extension + " created");
            } finally {
                writer.close();
            }
        }
    }

    /**
     *
     *
     * @param path
     *  Path of the file.
     * @param extension
     *  Extension for the file.
     * @param articles
     *  List of PubMed articles.
     * @throws IOException
     */
    public static void fileWithTitleContent(String path, String extension, List<PubmedArticle> articles) throws IOException {
        logger.info("Create Unindexed files");
        Writer writer = null;
        StringBuilder stringBuilder = null;

        for (PubmedArticle article : articles) {
            try {
                writer = FileGenerator.instanciateWriter(path, article.getPmid(), extension);
                stringBuilder = new StringBuilder();
                stringBuilder.append("T. ");
                stringBuilder.append(article.getTitleText());
                logger.info("Writer write title on file");
                writer.write(stringBuilder.toString());
                logger.info("File " + path + article.getPmid() + extension + " created");
            } finally {
                writer.close();
            }
        }
    }

    /**
     *
     *
     * @param path
     *  Path of the file.
     * @param extension
     *  Extension for the file.
     * @param articles
     *  List of PubMed articles.
     * @throws IOException
     */
    public static void fileWithAbstractContent(String path, String extension, List<PubmedArticle> articles) throws IOException {
        logger.info("Create Unindexed files");
        Writer writer = null;
        StringBuilder stringBuilder = null;

        for (PubmedArticle article : articles) {
            try {
                writer = FileGenerator.instanciateWriter(path, article.getPmid(), extension);
                stringBuilder = new StringBuilder();
                stringBuilder.append("A. ");
                stringBuilder.append(article.getAbstractText());
                logger.info("Writer write asbtract on file");
                writer.write(stringBuilder.toString());
                logger.info("File " + path + article.getPmid() + extension + " created");
            } finally {
                writer.close();
            }
        }
    }


    public static void fileIndexed(String path, String extension, List<PubmedArticle> articles) throws IOException {
        logger.info("Create indexed files");
        Writer writer = null;
        StringBuilder stringBuilder = null;

        for (PubmedArticle article : articles) {
            try {
                stringBuilder = new StringBuilder();
                stringBuilder.append("T. ");
                stringBuilder.append(article.getTitleText());
                stringBuilder.append(System.getProperty("line.separator"));
                stringBuilder.append("A. ");
                stringBuilder.append(article.getAbstractText());
                stringBuilder.append(System.getProperty("line.separator"));
                stringBuilder.append("M. ");
                writer = FileGenerator.instanciateWriter(path, article.getPmid() + "_indexed", extension);
                logger.info("Writer write content on file");
                writer.write(stringBuilder.toString());

                logger.info("Format all meshes");
                // Loop on each meshes.
                for (Mesh m : article.getMeshes()) {
                    stringBuilder = new StringBuilder();
                    // If size equal 0, then write only the description content.
                    if (m.getQualifiers().size() == 0) {
                        stringBuilder.append(m.getDescription());
                    }
                    // else, generate following format : desc/qual, desc/qual, ...
                    else {
                        for (String qualifier : m.getQualifiers()) {
                            stringBuilder.append(m.getDescription());
                            stringBuilder.append("/");
                            stringBuilder.append(qualifier);
                            stringBuilder.append("; ");
                        }
                    }
                    logger.info("Writer write meshes content on file.");
                    stringBuilder.append(" | ");
                    writer.write(stringBuilder.toString());
                    logger.info("Clean stringBuilder");
                }
                logger.info("File " + path + article.getPmid() + "_indexed" + extension + " created");
            } finally {
                writer.close();
            }
        }
    }

    private static Writer instanciateWriter(String path, String filename, String extension) throws FileNotFoundException {
        logger.info("Create file " + path + filename + extension + ".");
        return new BufferedWriter(
                new OutputStreamWriter(
                        new FileOutputStream(
                            path + filename + extension
                        )
                )
        );
    }
}
