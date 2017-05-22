package hu.barcode.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixHighLevelEncoder;
import org.krysalis.barcode4j.impl.datamatrix.SymbolShapeHint;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import hu.barcode.entities.BarcodeOrder;
import hu.barcode.entities.BarcodePrice;
import hu.barcode.entities.BarcodeUser;

@Component
@ComponentScan
public class ServicesUtil {

	@Autowired
	private JavaMailSender mailSender;

	public double getDatamatrixPrice(List<BarcodePrice> priceList, int charNum) {

		double price = 0;

		for (BarcodePrice bp : priceList) {
			if (bp.getCharFrom() <= charNum) {
				price += bp.getPrice();
			}
		}
		return price;
	}

	public double getGs1Price(List<BarcodePrice> priceList, int gs1Num) {
		double price = 0;

		if (priceList != null && !priceList.isEmpty()) {
			price = priceList.get(0).getPrice();
		}
		return price;
	}

	public String generateDatamatrix(String dataText, Long orderId) throws IOException {
		final String FILE_PREFIX = "DM0000";

		OutputStream out = null;
		int dpi = 200;

		String filePath = "c:/Temp/";
		String fileName = new String();

		fileName = filePath + FILE_PREFIX + orderId.toString() + ".PNG";

		DataMatrixBean codeGenDm = new DataMatrixBean();
		codeGenDm.setModuleWidth(UnitConv.in2mm(8.0f / dpi));
		codeGenDm.doQuietZone(false);
		codeGenDm.setShape(SymbolShapeHint.FORCE_SQUARE);

		try {
			DataMatrixHighLevelEncoder.encodeHighLevel(dataText);
		} catch (Exception e) {
			String st = e.getMessage();
		}

		try {
			out = new FileOutputStream(new File(fileName));
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", dpi,
					BufferedImage.TYPE_BYTE_BINARY, false, 0);
			codeGenDm.generateBarcode(canvas, dataText);
			canvas.finish();

		} catch (Exception e) {
			String error = e.getMessage();
		} finally {
			out.close();
		}

		return fileName;
	}

	public boolean sendEmailMessage(BarcodeOrder order, String filePath) {

		MimeMessage message = mailSender.createMimeMessage();
		try{
			message.setSubject(order.getUserId().getCompanyName() + " (Ez egy Barcode generator teszt");
			MimeMessageHelper mailMsg = new MimeMessageHelper(message, true);
			mailMsg.setFrom("ollegardony@t-online.hu");
			mailMsg.setTo(order.getUserId().getEmailAdress());
			mailMsg.setSubject(order.getUserId().getCompanyName() + "(Megrendelt Barcode küldése)");
			mailMsg.setText("Please find Attachment.");
			// FileSystemResource object for Attachment
			FileSystemResource file = new FileSystemResource(new File(filePath));
			mailMsg.addAttachment(order.getUserId().getCompanyName() + "-Barcode-"+order.getOrderNumber()+".PNG", file);
			mailSender.send(message);			
		}catch(Exception exp){
			return false;
		}

		return true;
	}
}
