package com.beitu.saas.common.utils.contract.utils;

import com.fqgj.log.factory.LogFactory;
import com.fqgj.log.interfaces.Log;

public class PDFHelper {
    
    private static Log LOGGER = LogFactory.getLog(PDFHelper.class);
    
    /**
     * PDF文件转PNG图片，全部页数
     *
     * @param PdfFilePath
     * @param dstImgFolder
     * @param dpi
     */
//    public static void pdf2Image(String PdfFilePath, String dstImgFolder, int dpi) {
//        File file = new File(PdfFilePath);
//        PDDocument pdDocument;
//        try {
//            String imgPDFPath = file.getParent();
//            int dot = file.getName().lastIndexOf('.');
//            String imagePDFName = file.getName().substring(0, dot); // 获取图片文件名
//            String imgFolderPath = null;
//            if (dstImgFolder.equals("")) {
//                imgFolderPath = imgPDFPath + File.separator + imagePDFName;// 获取图片存放的文件夹路径
//            } else {
//                imgFolderPath = dstImgFolder + File.separator + imagePDFName;
//            }
//
//            if (FileHelper.createDirectory(imgFolderPath)) {
//                pdDocument = PDDocument.load(file);
//                PDFRenderer renderer = new PDFRenderer(pdDocument);
//                /* dpi越大转换后越清晰，相对转换速度越慢 */
//                int pages = pdDocument.getNumberOfPages();
//                StringBuffer imgFilePath = null;
//                for (int i = 0; i < pages; i++) {
//                    String imgFilePathPrefix = imgFolderPath + File.separator + imagePDFName;
//                    imgFilePath = new StringBuffer();
//                    imgFilePath.append(imgFilePathPrefix);
//                    imgFilePath.append("_");
//                    imgFilePath.append(String.valueOf(i + 1));
//                    imgFilePath.append(".png");
//                    File dstFile = new File(imgFilePath.toString());
//                    BufferedImage image = renderer.renderImageWithDPI(i, dpi);
//                    ImageIO.write(image, "png", dstFile);
//                }
//                System.out.println("PDF文档转PNG图片成功！");
//
//            } else {
//                LOGGER.info("PDF文档转PNG图片失败：" + "创建" + imgFolderPath + "失败");
//            }
//
//        } catch (IOException e) {
//            LOGGER.info("PDF文档转PNG图片异常：" + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}