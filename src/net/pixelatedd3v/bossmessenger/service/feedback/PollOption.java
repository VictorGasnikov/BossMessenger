package net.pixelatedd3v.bossmessenger.service.feedback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PollOption complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="PollOption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Id" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ItemName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ItemType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PollOption", propOrder = {"id", "itemName", "itemType"})
public class PollOption {

	@XmlElement(name = "Id")
	protected int id;
	@XmlElement(name = "ItemName")
	protected String itemName;
	@XmlElement(name = "ItemType")
	protected String itemType;

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
	 * Gets the value of the itemName property.
	 *
	 * @return possible object is
	 * {@link String }
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * Sets the value of the itemName property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setItemName(String value) {
		this.itemName = value;
	}

	/**
	 * Gets the value of the itemType property.
	 *
	 * @return possible object is
	 * {@link String }
	 */
	public String getItemType() {
		return itemType;
	}

	/**
	 * Sets the value of the itemType property.
	 *
	 * @param value allowed object is
	 *              {@link String }
	 */
	public void setItemType(String value) {
		this.itemType = value;
	}

}
