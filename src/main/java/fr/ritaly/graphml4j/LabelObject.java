package fr.ritaly.graphml4j;

import java.awt.Color;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import org.apache.commons.lang.Validate;

import fr.ritaly.graphml4j.base.Alignment;
import fr.ritaly.graphml4j.base.FontStyle;

final class LabelObject {

	private boolean visible = true;

	private Color textColor = Color.BLACK;

	// This color is optional
	private Color backgroundColor = null;

	private Alignment textAlignment = Alignment.CENTER;

	private FontStyle fontStyle = FontStyle.PLAIN;

	private String fontFamily = "Dialog";

	private int fontSize = 12;

	private boolean underlinedText = false;

	public LabelObject() {
	}

	public LabelObject(LabelObject object) {
		apply(object);
	}

	void apply(LabelObject object) {
		Validate.notNull(object, "The given shape object is null");

		this.visible = object.visible;
		this.textColor = object.textColor;
		this.textAlignment = object.textAlignment;
		this.fontStyle = object.fontStyle;
		this.fontFamily = object.fontFamily;
		this.fontSize = object.fontSize;
		this.underlinedText = object.underlinedText;
		this.backgroundColor = object.backgroundColor;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		// This color is optional
		this.backgroundColor = backgroundColor;
	}

	public boolean isUnderlinedText() {
		return underlinedText;
	}

	public void setUnderlinedText(boolean value) {
		this.underlinedText = value;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color color) {
		Validate.notNull(color, "The given text color is null");

		this.textColor = color;
	}

	public int getFontSize() {
		return fontSize;
	}

	public void setFontSize(int fontSize) {
		Validate.isTrue(fontSize > 0, String.format("The given font size (%d) must be positive", fontSize));

		this.fontSize = fontSize;
	}

	public String getFontFamily() {
		return fontFamily;
	}

	public void setFontFamily(String fontFamily) {
		Validate.notNull(fontFamily, "The given font family is null");

		this.fontFamily = fontFamily;
	}

	public Alignment getTextAlignment() {
		return textAlignment;
	}

	public void setTextAlignment(Alignment textAlignment) {
		Validate.notNull(textAlignment, "The given text alignment is null");

		this.textAlignment = textAlignment;
	}

	public FontStyle getFontStyle() {
		return fontStyle;
	}

	public void setFontStyle(FontStyle fontStyle) {
		Validate.notNull(fontStyle, "The given font style is null");

		this.fontStyle = fontStyle;
	}

	void writeTo(XMLStreamWriter writer, String label) throws XMLStreamException {
		Validate.notNull(writer, "The given stream writer is null");

		// y:NodeLabel
		writer.writeStartElement("y:NodeLabel");
		writer.writeAttribute("alignement", textAlignment.getValue());
		writer.writeAttribute("fontFamily", fontFamily);
		writer.writeAttribute("fontSize", Integer.toString(fontSize));
		writer.writeAttribute("fontStyle", fontStyle.getValue());

		if (backgroundColor != null) {
			writer.writeAttribute("backgroundColor", Utils.encode(backgroundColor));
		} else {
			writer.writeAttribute("hasBackgroundColor", "false");
		}

		writer.writeAttribute("hasLineColor", Boolean.toString(false)); // TODO Implement this
		writer.writeAttribute("textColor", Utils.encode(textColor));
		writer.writeAttribute("visible", Boolean.toString(visible));

		if (underlinedText) {
			writer.writeAttribute("underlinedText", Boolean.toString(underlinedText));
		}
//		if (nodeStyle.hasInsets()) { TODO Implement this
//			writer.writeAttribute("bottomInset", Integer.toString(nodeStyle.getBottomInset()));
//			writer.writeAttribute("topInset", Integer.toString(nodeStyle.getTopInset()));
//			writer.writeAttribute("leftInset", Integer.toString(nodeStyle.getLeftInset()));
//			writer.writeAttribute("rightInset", Integer.toString(nodeStyle.getRightInset()));
//		}

		writer.writeCharacters(label);
		writer.writeEndElement(); // </y:NodeLabel>
	}
}