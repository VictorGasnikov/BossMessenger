package net.pixelatedd3v.bossmessenger.service.feedback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for LoadedPoll complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="LoadedPoll">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Title" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Options" type="{http://pixelatedev.com/bossmessenger/feedback}ArrayOfPollOption" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "LoadedPoll", propOrder = {"id", "title", "options"})
public class LoadedPoll {

	@XmlElement(name = "Id")
	protected int id;
	@XmlElement(name = "Title")
	protected String title;
	@XmlElement(name = "Options")
	protected ArrayOfPollOption options;

	/**
	 * Gets the value of the id property.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the value of the id property.
	 */
	public void setId(int value) {
		this.id = value;
	}

	/**
	 * Gets the value of the title property.
	 *
	 * @return possible object is
	 * {@link String }
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the value of the title property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setTitle(String value) {
		this.title = value;
	}

	/**
	 * Gets the value of the options property.
	 *
	 * @return possible object is
	 * {@link ArrayOfPollOption }
	 */
	public ArrayOfPollOption getOptions() {
		return options;
	}

	/**
	 * Sets the value of the options property.
	 *
	 * @param value allowed object is
	 *              {@link ArrayOfPollOption }
	 */
	public void setOptions(ArrayOfPollOption value) {
		this.options = value;
	}

}
