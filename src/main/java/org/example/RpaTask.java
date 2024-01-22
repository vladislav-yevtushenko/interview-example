package org.example;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.example.page.InvoicePage;
import org.example.page.LoginPage;
import org.example.repository.InputDto;
import org.example.repository.InputRepository;
import org.openqa.selenium.WebDriver;

public class RpaTask {

    private InputRepository inputEntityRepository;

    // AWS credentials
    private String accessKey = "YOUR_ACCESS_KEY";
    private String secretKey = "YOUR_SECRET_KEY";
    // The bucket and key for the byte[] to be uploaded
    private String bucketName = "YOUR_BUCKET_NAME";
    private String keyName = "YOUR_KEY_NAME";
    private WebDriver driver;

    public RpaTask(InputRepository inputEntityRepository) {
        this.inputEntityRepository = inputEntityRepository;
    }

    public void process() throws IOException, AWTException {

        try {
            List objects = inputEntityRepository.getAll();
            login();
            for (Object object : objects) {
                getFillInvoice(object);
            }

        } catch (Throwable e) {
            e.printStackTrace();
            // define screen dimension x and y
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int screenWidth = (int) screenSize.getWidth();
            int screenHeight = (int) screenSize.getHeight();
            byte[] bytes = screenshot(screenHeight, screenWidth);
            saveScreenShot(bytes);
        }
    }

    public byte[] screenshot(Integer x, Integer y) throws AWTException, IOException {
        Robot robot = new Robot();
        Rectangle screenRect = new Rectangle(x, y);
        BufferedImage capture = robot.createScreenCapture(screenRect);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(capture, "png", baos);
        byte[] screenshot = baos.toByteArray();
        return screenshot;
    }

    public void saveScreenShot(byte[] screenshot) {
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_EAST_1).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

        // Save the byte[] to S3
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(screenshot.length);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(screenshot);
        PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, keyName, byteArrayInputStream, metadata);
        s3Client.putObject(putObjectRequest);
    }

    private void getFillInvoice(Object object) {
        InvoicePage invoicePage = new InvoicePage(driver);
        if (object instanceof InputDto) {
            InputDto inputDto = (InputDto) object;

        }
    }

    private void login() {
        driver.get("http://invoices.com");
        LoginPage loginPage = new LoginPage();
        loginPage.setLogin("user1");
        loginPage.setPassword("user2");
        loginPage.clickLoginButton();
    }

}