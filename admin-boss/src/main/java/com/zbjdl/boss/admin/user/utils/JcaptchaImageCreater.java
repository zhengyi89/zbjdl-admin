package com.zbjdl.boss.admin.user.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 生成简单的验证码，验证码由字母和数字组成。<br>
 * 
 * <p>
 * 该验证码生成器的使用方法如下。<br>
 * 在web.xml里的配置：
 * 
 * <pre>
 * <code>
 *   &lt;servlet&gt;
 *      &lt;servlet-name&gt;CAPTCHA&lt;/servlet-name&gt;
 *      &lt;servlet-class&gt;wen.bo.tian.mind.manager.tools.SecurityCodeServlet&lt;/servlet-class&gt;
 *   &lt;/servlet&gt;
 *  
 *   &lt;servlet-mapping&gt;
 *      &lt;servlet-name&gt;CAPTCHA&lt;/servlet-name&gt;
 *      &lt;url-pattern&gt;/CAPTCHA.GIF&lt;/url-pattern&gt;
 *   &lt;/servlet-mapping&gt;
 * </code>
 * </pre>
 * 
 * 在 页面里的配置：
 * 
 * <pre>
 * <code>
 * &lt;img id="captchaImg" src="CAPTCHA.GIF" /&gt
 * </code>
 * </pre>
 * 
 * 通过session可以取得验证码的值：
 * 
 * <pre>
 * <code>
 * String captcha = session.getAttribute("SecurityCode").toString();
 * </code>
 * </pre>
 * 
 * 
 */

@Controller
@RequestMapping("/captcha")
public class JcaptchaImageCreater {
	private static final Logger logger = LoggerFactory.getLogger(JcaptchaImageCreater.class);

	public static final String SESSION_KEY_SECURITY_CODE = "SecurityCode";

	protected static final int CAPITAL_ASCII_INDEX = 65;

	protected static final int LOWER_ASCII_INDEX = 97;

	protected static final int INTEGER_ASCII_INDEX = 48;

	protected static final int RANGE_LETTER = 25;

	protected static final int RANGE_INTEGER = 9;

	protected static final int RANGE_COLOR = 255;

	protected static final int IMG_WIDTH = 80;

	protected static final int IMG_HEIGHT = 30;

	protected Font imgFont = new Font("Arial Black", Font.ITALIC, 28);

	private String securityCode = "";

	@RequestMapping
	public void handleRequest(HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/gif");
		HttpSession session = request.getSession();
		try {	
			ServletOutputStream vout = response.getOutputStream();

			BufferedImage image = drawSecurityCode(IMG_WIDTH, IMG_HEIGHT);

			// JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(vout);
			// encoder.encode(image);
			ImageIO.write(image, "GIF", vout);
			vout.close();

		} catch (IOException e) {
			logger.error(e.getMessage());
		}

		if (session.getAttribute(SESSION_KEY_SECURITY_CODE) != null)
			session.removeAttribute(SESSION_KEY_SECURITY_CODE);
		session.setAttribute(SESSION_KEY_SECURITY_CODE, securityCode);
	}

	/**
	 * 根据指定的宽度和高度画出验证码图片
	 * 
	 * @param width
	 *            验证码图片的宽度
	 * @param height
	 *            验证码图片的高度
	 * @return 验证码图片
	 */
	private BufferedImage drawSecurityCode(int width, int height) {
		BufferedImage Bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics graph = Bimage.getGraphics();

		Random rand = new Random();
		rand.setSeed(System.currentTimeMillis());
		graph.setColor(getRandomColor(200, 250));
		graph.fillRect(0, 0, width, height);
		graph.setFont(imgFont);

		// 产生新的验证码之前清空验证码
		securityCode = "";

		for (int i = 0; i < 4; i++) {
			int vPos = rand.nextInt(3);
			int hPos = rand.nextInt(3);
			String randomChar = getRandomChar();
			securityCode = securityCode + randomChar;

			graph.setColor(new Color(20 + rand.nextInt(110), 20 + rand.nextInt(110), 20 + rand.nextInt(110)));
			graph.drawString(randomChar, 18 * i + vPos, 20 + hPos);
		}
		return Bimage;
	}

	/**
	 * 根据字体颜色和背景颜色获得随即颜色
	 * 
	 * @param fontColor
	 *            字体颜色
	 * @param backgroudColor
	 *            背景颜色
	 * @return 随机颜色
	 */
	private Color getRandomColor(int fontColor, int backgroudColor) {
		if (fontColor > RANGE_COLOR)
			fontColor = RANGE_COLOR;
		if (backgroudColor > RANGE_COLOR)
			backgroudColor = RANGE_COLOR;
		Random rand = new Random();
		int r = fontColor + rand.nextInt(backgroudColor - fontColor);
		int g = fontColor + rand.nextInt(backgroudColor - fontColor);
		int b = fontColor + rand.nextInt(backgroudColor - fontColor);
		return new Color(r, g, b);
	}

	/**
	 * 获得随机字母
	 * 
	 * @return 随机字母
	 */
	private String getRandomChar() {
		Random random = new Random();
		int randomCondition = (int) Math.round(Math.random() * 2);
		switch (randomCondition) {
		case 1:
			return String.valueOf((char) (CAPITAL_ASCII_INDEX + random.nextInt(RANGE_LETTER)));
		case 2:
			return String.valueOf((char) (LOWER_ASCII_INDEX + random.nextInt(RANGE_LETTER)));
		default:
			return String.valueOf((char) (INTEGER_ASCII_INDEX + random.nextInt(RANGE_INTEGER)));
		}
	}

}
