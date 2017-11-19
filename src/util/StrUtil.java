package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StrUtil {

	public static String getNullString(String str) {
		return (str == null) ? "" : str;
	}

	public static String toHtml(String str) {
		if (str == null || "".equals(str))
			return "";
		java.lang.StringBuffer buf = new java.lang.StringBuffer(str.length() + 6);
		char ch = ' ';
		for (int i = 0; i < str.length(); i++) {
			ch = str.charAt(i);
			if (ch == '<')
				buf.append("&lt;");
			else {
				if (ch == '>')
					buf.append("&gt;");
				else {
					if (ch == ' ')
						buf.append("&nbsp;");
					else {
						if (ch == '\n')
							buf.append("<br>");
						else {
							if (ch == '\'')
								buf.append("&#039;");
							else {
								if (ch == '\"')
									buf.append("&quot;");
								else
									buf.append(ch);
							}
						}
					}
				}
			}
		}
		str = buf.toString();
		return str;
	}

	public static String trace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		t.printStackTrace(pw);
		pw.flush();
		String result = sw.toString();
		return result;
	}

	 
	public static boolean isBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return true;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}

		return true;
	}

	 
	public static boolean isNotBlank(String str) {
		int length;

		if ((str == null) || ((length = str.length()) == 0)) {
			return false;
		}

		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}

		return false;
	}

	 
	public static String omitRight(String str, int len) {
		if (str == null) {
			return "";
		}

		String reStr;
		if (len < 1 || str.length() <= len) {
			reStr = str;
		} else if (len <= 3) {
			reStr = str.substring(0, len);
		} else {
			reStr = str.substring(0, len - 3) + "...";
		}

		return reStr;
	}

	 
	public static String omitCenter(String str, int len) {
		if (str == null) {
			return "";
		}

		String reStr;
		if (len < 1 || str.length() <= len) {
			reStr = str;
		} else if (len <= 3) {
			reStr = str.substring(0, len);
		} else {
			int endLen = (len - 3) / 2;
			int beginLen = len - 3 - endLen;
			reStr = str.substring(0, beginLen) + "..." + str.substring(str.length() - endLen);
		}

		return reStr;
	}

	 
	public static boolean hasFullSize(String inStr) {

		if (inStr.getBytes().length != inStr.length()) {
			return true;
		}
		return false;
	}

	 
	public static boolean checkGBK(String str) {
		String test = "[\\u4E00-\\u9FA5]+";
		Pattern p = Pattern.compile(test);
		Matcher m = p.matcher(str);
		if (m.find()) {
			return true;
		}
		return false;
	}
}
