package hu.barcode.services;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.MimeMessage;

import org.krysalis.barcode4j.BarcodeDimension;
import org.krysalis.barcode4j.ChecksumMode;
import org.krysalis.barcode4j.impl.code128.Code128Constants;
import org.krysalis.barcode4j.impl.code128.EAN128Bean;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixBean;
import org.krysalis.barcode4j.impl.datamatrix.DataMatrixHighLevelEncoder;
import org.krysalis.barcode4j.impl.datamatrix.SymbolShapeHint;
import org.krysalis.barcode4j.output.bitmap.BitmapBuilder;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoder;
import org.krysalis.barcode4j.output.bitmap.BitmapEncoderRegistry;
import org.krysalis.barcode4j.output.java2d.Java2DCanvasProvider;
import org.krysalis.barcode4j.tools.UnitConv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import hu.barcode.entities.BarcodeOrder;
import hu.barcode.entities.BarcodePrice;

/**
 * @author Ollé Csaba
 * @project Generate Barcode
 * @Created 18/05/2017
 *          Service utils bean
 *          Generate Barcode (Datamatrix, GS1), Calculate price, send email, check mail adress
 */
@Component
@ComponentScan
@PropertySource("classpath:barcode.properties")
public class ServicesUtil {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	protected final char FNC1 = 29;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private Environment env;

	// Get price for datamatrix
	public double getDatamatrixPrice(List<BarcodePrice> priceList, int charNum) {

		double price = 0;

		for (BarcodePrice bp : priceList) {
			if (bp.getCharFrom() <= charNum) {
				price += bp.getPrice();
			}
		}
		return price;
	}

	// get price for Gs1
	public double getGs1Price(List<BarcodePrice> priceList, int gs1Num) {
		double price = 0;

		if (priceList != null && !priceList.isEmpty()) {
			price = priceList.get(0).getPrice();
		}
		return price;
	}

	// Generate Datamatrix
	public String generateDatamatrix(String dataText, Long orderId) throws IOException, UnsupportedOperationException, RuntimeException {

		OutputStream out = null;

		String fileName = addFilepath(orderId.toString());
		int dpi = Integer.valueOf(env.getProperty("barcode.dpi"));

		DataMatrixBean codeGenDm = new DataMatrixBean();
		codeGenDm.setModuleWidth(UnitConv.in2mm(8.0f / dpi));
		codeGenDm.doQuietZone(false);
		codeGenDm.setShape(SymbolShapeHint.FORCE_SQUARE);

		try {
			DataMatrixHighLevelEncoder.encodeHighLevel(dataText);
		} catch (Exception e) {
			UnsupportedOperationException exp = new UnsupportedOperationException(e.getMessage());
			throw exp;
		}

		try {
			out = new FileOutputStream(new File(fileName));
			BitmapCanvasProvider canvas = new BitmapCanvasProvider(out, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
			codeGenDm.generateBarcode(canvas, dataText);
			canvas.finish();

		} catch (Exception e) {
			RuntimeException re = new RuntimeException(e.getMessage());
			throw re;
		} finally {
			out.close();
		}

		return fileName;
	}

	// Generate GS1
	public String generateGs1(String gs1Code, Long orderId) throws IOException, UnsupportedOperationException, RuntimeException {

		String fileName = addFilepath(orderId.toString());
		int dpi = Integer.valueOf(env.getProperty("barcode.dpi"));
		OutputStream out = null;

		EAN128Bean codeGen = null;
		codeGen = new EAN128Bean();
		codeGen.setChecksumMode(ChecksumMode.CP_CHECK);
		codeGen.setCodeset(Code128Constants.CODESET_ALL);
		DataMatrixBean dm = new DataMatrixBean();
		dm.setModuleWidth(UnitConv.in2mm(8.0f / dpi));
		dm.setShape(SymbolShapeHint.FORCE_SQUARE);
		dm.doQuietZone(false);

		out = new FileOutputStream(new File(fileName));

		try {
			BarcodeDimension dim = dm.calcDimensions(gs1Code);
			int orientation = 0;
			String mime = "image/x-png";
			int resolution = BufferedImage.TYPE_BYTE_BINARY;
			BufferedImage image = BitmapBuilder.prepareImage(dim, orientation, dpi, resolution);

			Graphics2D g2d = BitmapBuilder.prepareGraphics2D(image, dim, orientation, false);

			Java2DCanvasProvider canvas = new Java2DCanvasProvider(g2d, orientation);
			dm.generateBarcode(canvas, gs1Code);
			g2d.dispose();
			image.flush();
			if (out != null) {
				final BitmapEncoder encoder = BitmapEncoderRegistry.getInstance(mime);
				encoder.encode(image, out, mime, resolution);
			}
		} catch (Exception e) {
			RuntimeException re = new RuntimeException(e.getMessage());
			throw re;
		} finally {
			out.close();
		}
		return fileName;
	}

	// Send email
	public boolean sendEmailMessage(BarcodeOrder order, String filePath) {

		MimeMessage message = mailSender.createMimeMessage();
		try {

			MimeMessageHelper mailMsg = new MimeMessageHelper(message, true);
			mailMsg.setFrom(env.getProperty("email.sender"));
			mailMsg.setTo(order.getUserId().getEmailAdress());
			mailMsg.setSubject(order.getUserId().getCompanyName() + " " + env.getProperty("email.subjectPlus"));
			mailMsg.setText(env.getProperty("email.text"));
			// FileSystemResource object for Attachment
			FileSystemResource file = new FileSystemResource(new File(filePath));
			mailMsg.addAttachment(order.getUserId().getCompanyName() + "-Barcode-" + order.getOrderNumber() + ".PNG", file);
			mailSender.send(message);
		} catch (Exception exp) {
			return false;
		}

		return true;
	}

	// Check email adress
	public boolean emailValidation(String emailAdress) {
		Pattern pattern;
		Matcher matcher;
		pattern = Pattern.compile(EMAIL_PATTERN);

		matcher = pattern.matcher(emailAdress);
		return matcher.matches();
	}

	public String addFilepath(String orderId) {

		String filePath = env.getProperty("barcode.save.filepath");
		String filePrefix = env.getProperty("order.number.prefix");
		String fileName = filePath + filePrefix + orderId.toString() + ".PNG";

		return fileName;
	}

	// Parse gs1 128 AI
	public String getGS1Code(String code) {

		int pos = code.indexOf("-");

		String retStr = code.substring(0, pos);
		retStr += code.substring(pos + 1, code.length());

		return retStr;
	}

}
