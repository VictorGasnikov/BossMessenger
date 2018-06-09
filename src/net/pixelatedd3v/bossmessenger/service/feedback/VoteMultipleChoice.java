package net.pixelatedd3v.bossmessenger.service.feedback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="pollId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="optionId" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"pollId", "optionId"})
@XmlRootElement(name = "VoteMultipleChoice")
public class VoteMultipleChoice {

	protected int pollId;
	protected int optionId;

	/**
	 * Gets the value of the pollId property.
	 */
	public int getPollId() {
		return pollId;
	}

	/**
	 * Sets the value of the pollId property.
	 */
	public void setPollId(int value) {
		this.pollId = value;
	}

	/**
	 * Gets the value of the optionId property.
	 */
	public int getOptionId() {
		return optionId;
	}

	/**
	 * Sets the value of the optionId property.
	 */
	public void setOptionId(int value) {
		this.optionId = value;
	}

}
