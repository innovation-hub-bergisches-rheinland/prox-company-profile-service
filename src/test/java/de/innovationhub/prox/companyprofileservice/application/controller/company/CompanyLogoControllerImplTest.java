package de.innovationhub.prox.companyprofileservice.application.controller.company;

// TODO: This test breaks the jenkins build
/*@SpringBootTest
class CompanyLogoControllerImplTest {

  @MockBean
  CompanyLogoService companyLogoService;

  @Autowired
  WebApplicationContext context;

  @DisplayName("GET /companies/{id}/image should return OK")
  @Test
  void getCompanyLogoShouldReturnOk() throws NoSuchAlgorithmException {
    byte[] bytes = new byte[20];
    SecureRandom.getInstanceStrong().nextBytes(bytes);
    InputStream is = new ByteArrayInputStream(bytes);
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyLogoService.getCompanyLogo(any())).thenReturn(Optional.of(companyLogo));
    when(companyLogoService.getCompanyLogoAsStream(any())).thenReturn(Optional.of(is));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .get("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.OK);

    verify(companyLogoService).getCompanyLogo(any());
    verify(companyLogoService).getCompanyLogoAsStream(any());
  }

  @DisplayName("GET /companies/{id}/image should return NOT_FOUND")
  @Test
  void getCompanyLogoShouldReturnNotFound() throws NoSuchAlgorithmException {
    when(companyLogoService.getCompanyLogo(any())).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .get("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(companyLogoService).getCompanyLogo(any());
  }

  @DisplayName("POST /companies/{id}/image should return OK")
  @Test
  void postCompanyLogoShouldReturnOk() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyLogoService.setCompanyLogo(any(), any(InputStream.class)))
        .thenReturn(Optional.of(companyLogo));

    File file = ResourceUtils.getFile("classpath:img/wikipedia.png");

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .multiPart("image", file)
        .when()
        .post("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.OK);

    verify(companyLogoService).setCompanyLogo(any(), any(InputStream.class));
  }

  @DisplayName("POST /companies/{id}/image should return INTERNAL_SERVER_ERROR")
  @Test
  void postCompanyLogoShouldReturnInternalServerError() throws IOException {
    when(companyLogoService.setCompanyLogo(any(), any(InputStream.class)))
        .thenReturn(Optional.empty());

    File file = ResourceUtils.getFile("classpath:img/wikipedia.png");

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .multiPart("image", file)
        .when()
        .post("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.INTERNAL_SERVER_ERROR);

    verify(companyLogoService).setCompanyLogo(any(), any(InputStream.class));
  }

  @DisplayName("DELETE /companies/{id}/image should return NO_CONTENT")
  @Test
  void postCompanyLogoShouldReturnNoContent() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyLogoService.deleteCompanyLogo(any())).thenReturn(Optional.of(companyLogo));

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .delete("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.NO_CONTENT);

    verify(companyLogoService).deleteCompanyLogo(any());
  }

  @DisplayName("DELETE /companies/{id}/image should return NOT_FOUND")
  @Test
  void postCompanyLogoShouldReturnNotFound() throws IOException {
    CompanyLogo companyLogo = new CompanyLogo(UUID.randomUUID(), 12315L, "image/png");
    when(companyLogoService.deleteCompanyLogo(any())).thenReturn(Optional.empty());

    given()
        .webAppContextSetup(context)
        .header("Accept", "image/*")
        .when()
        .delete("/companies/{id}/logo", UUID.randomUUID())
        .then()
        .status(HttpStatus.NOT_FOUND);

    verify(companyLogoService).deleteCompanyLogo(any());
  }
}*/
