package com.outfit.planner.system.domain;

import com.outfit.planner.system.domain.dto.create.CreateProductRequest;
import com.outfit.planner.system.domain.ports.input.service.ProductApplicationService;
import com.outfit.planner.system.domain.repository.ProductRepository;
import com.outfit.planner.system.product.service.domain.GetProductsCriteria;
import com.outfit.planner.system.product.service.domain.entity.Product;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameConverter;
import org.bytedeco.javacv.Java2DFrameUtils;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.global.opencv_imgproc;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Rect;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Service
public class ProductMockService {

    private final ProductApplicationService productApplicationService;

    private final ProductRepository productRepository;

    public ProductMockService(ProductApplicationService productApplicationService, ProductRepository productRepository) {
        this.productApplicationService = productApplicationService;
        this.productRepository = productRepository;
    }

    @PostConstruct
    public void createMocks() {
        if (areMocksAlreadyCreated()) return;

        String folderPath = "mock/products"; // Replace with the relative path to your resources folder

        try {
            iterateResourceFolders(folderPath);
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean areMocksAlreadyCreated() {
        GetProductsCriteria criteria = new GetProductsCriteria();
        criteria.setUsername("mock");
        List<Product> products = productRepository.getProducts(criteria);

        return !products.isEmpty();
    }

    public void iterateResourceFolders(String folderPath) throws IOException, URISyntaxException {
        Path path = Paths.get(getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
        try (JarFile jarFile = new JarFile(path.toFile())) {
            Enumeration<JarEntry> entries = jarFile.entries();

            List<JarEntry> entryList = Collections.list(entries);
            entryList.sort(Comparator.comparing(JarEntry::getName));

            for (JarEntry entry : entryList) {
                String entryName = entry.getName();
                if (entryName.startsWith(folderPath + "/") && !entry.isDirectory()) {
                    int lastIndex = entryName.lastIndexOf("/");
                    int secondLastIndex = entryName.lastIndexOf("/", lastIndex - 1);
                    String categoryName = entryName.substring(secondLastIndex + 1, lastIndex);

                    byte[] imageBytes = readImage(jarFile, entry);

//                    Frame frame = loadFrame(imageBytes);
//                    frame = processImage(frame);
//                    frame = trimObject(frame);
//                    byte[] processedImage = frameToBytes(frame);

                    String image = Base64.getEncoder().encodeToString(imageBytes);
                    createMockedProduct(categoryName.toUpperCase(), image);
                }
            }
        }
    }

    private Frame trimObject(Frame frame){

        // Convert the Frame to OpenCV's Mat format
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        Mat matFrame = converter.convert(frame);

        // Convert the image to grayscale
        Mat grayImage = new Mat();
        opencv_imgproc.cvtColor(matFrame, grayImage, opencv_imgproc.COLOR_BGR2GRAY);

        // Apply binary thresholding to segment the object from the background
        Mat binaryImage = new Mat();
        opencv_imgproc.threshold(grayImage, binaryImage, 0, 255, opencv_imgproc.THRESH_BINARY | opencv_imgproc.THRESH_OTSU);

        // Find the contour representing the object
        MatVector contours = new MatVector();
        opencv_imgproc.findContours(binaryImage, contours, opencv_imgproc.RETR_EXTERNAL, opencv_imgproc.CHAIN_APPROX_SIMPLE);

        // Find the bounding box of the object
        Rect boundingRect = opencv_imgproc.boundingRect(contours.get(0));

        // Crop the image using the bounding box
        Mat croppedImage = new Mat(matFrame, boundingRect);

        // Convert the cropped image back to Frame format
        Frame outputFrame = converter.convert(croppedImage);
        return outputFrame;
    }

    private Frame processImage(Frame frame) {

        // Convert the Frame to OpenCV's Mat format
        OpenCVFrameConverter.ToMat converter = new OpenCVFrameConverter.ToMat();
        Mat matFrame = converter.convert(frame);

        // Perform background removal
        Mat foregroundMask = new Mat();
        Mat backgroundImage = new Mat();
        opencv_imgproc.grabCut(matFrame, foregroundMask, new Rect(), backgroundImage, new Mat(), 5, opencv_imgproc.GC_INIT_WITH_RECT);
        Mat foreground = new Mat();
        opencv_core.bitwise_and(matFrame, matFrame, foreground, foregroundMask);

        // Display or save the resulting foreground image
        Frame outputFrame = converter.convert(foreground);
        return outputFrame;
//        return frameToBytes(outputFrame);
    }

    private Frame loadFrame(byte[] frameBytes){
            try {
                // Convert bytes to BufferedImage
                ByteArrayInputStream bis = new ByteArrayInputStream(frameBytes);
                BufferedImage bufferedImage = ImageIO.read(bis);

                // Convert BufferedImage to Frame
                Frame frame = Java2DFrameUtils.toFrame(bufferedImage);

                // Process the frame as needed
                // ...

                return frame;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }

    private byte[] frameToBytes(Frame frame){
        try {
            // Convert Frame to BufferedImage
            BufferedImage bufferedImage = Java2DFrameUtils.toBufferedImage(frame);

            // Convert BufferedImage to bytes
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "jpg", baos);
            baos.flush();
            byte[] frameBytes = baos.toByteArray();
            baos.close();

            return frameBytes;
            // Use the frameBytes as needed
            // ...

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static byte[] readImage(JarFile jarFile, JarEntry entry) throws IOException {
        byte[] imageBytes;
        try (var inputStream = jarFile.getInputStream(entry)) {
            imageBytes = inputStream.readAllBytes();
        }
        return imageBytes;
    }

    public void createMockedProduct(String category, String image) {
        CreateProductRequest request = new CreateProductRequest();
        request.setId(UUID.randomUUID());
        request.setUsername("mock");
        request.setImage(image);
        request.setCategory(category);

        productApplicationService.createProduct(request);
    }


}
