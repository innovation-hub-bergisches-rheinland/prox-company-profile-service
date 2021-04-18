package de.innovationhub.prox.companyprofileservice.domain.company;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/** A repository which is capable of resolving a CompanyImage into image data */
public interface CompanyImageRepository {

  /**
   * Load the image data from filename
   *
   * @param filename filename in which the image is stored
   * @return image data
   * @throws IOException on I/O error
   */
  Optional<byte[]> getCompanyImage(Path filepath);

  default Optional<byte[]> getCompanyImage(String filepath) {
    return getCompanyImage(Paths.get(filepath));
  }

  /**
   * Save the image data in a file and return the filename
   *
   * @param data image data
   * @return filename (generated)
   * @throws IOException on I/O error
   */
  String saveCompanyImage(byte[] data) throws IOException;

  /**
   * Deletes the image file at provided filename
   *
   * @param filename filename to delete
   * @return true if deletion was successful, otherwise false
   * @throws IOException on I/O error
   */
  boolean deleteCompanyImage(Path filepath) throws IOException;

  default boolean deleteCompanyImage(String filepath) throws IOException {
    return deleteCompanyImage(Paths.get(filepath));
  }

  /**
   * Checks if the image file exists
   *
   * @param filename filename to check
   * @return true if it exists otherwise false
   */
  boolean imageExists(Path filepath);

  default boolean imageExists(String filepath) {
    return imageExists(Paths.get(filepath));
  }

  Optional<byte[]> getDefaultImage();
}
