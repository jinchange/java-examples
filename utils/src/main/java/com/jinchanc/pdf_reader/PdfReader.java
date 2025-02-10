package com.jinchanc.pdf_reader;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @author zhangjin@algorix.co
 * @since 2025/2/6 11:50
 */
public class PdfReader {

    public static void main(String[] args) {
        String path = "/Users/admin/Downloads/RixengineCN ADX 接口文档.pdf";
        File file = new File(path);
        // 尽可能使用try-with-resource代替try-catch-finally
        try (PDDocument document = PDDocument.load(file)) {
            int pageSize = document.getNumberOfPages();
            // 一页一页读取
            for (int i = 0; i < pageSize; i++) {
                // 文本内容
                PDFTextStripper stripper = new PDFTextStripper();
                // 设置按顺序输出
                stripper.setSortByPosition(true);
                stripper.setStartPage(i + 1);
                stripper.setEndPage(i + 1);
                String text = stripper.getText(document);
                System.out.println(text.trim());
                System.out.println("-=-=-=-=-=-=-=-=-=-=-=-=-");

                // 图片内容
//                PDPage page = document.getPage(i);
//                PDResources resources = page.getResources();
//                Iterable<COSName> cosNames = resources.getXObjectNames();
//                if (cosNames != null) {
//                    for (COSName cosName : cosNames) {
//                        if (resources.isImageXObject(cosName)) {
//                            PDImageXObject ipdmage = (PDImageXObject) resources.getXObject(cosName);
//                            BufferedImage image = ipdmage.getImage();
//                            try (FileOutputStream out = new FileOutputStream(
//                                    "D:\\temp\\PdfboxReadTextAndImage\\" + UUID.randomUUID() + ".png")) {
//                                ImageIO.write(image, "png", out);
//                            } catch (IOException ignored) {
//                            }
//                        }
//                    }
//                }
            }
        } catch (IOException ignored) {
        }
    }
}
