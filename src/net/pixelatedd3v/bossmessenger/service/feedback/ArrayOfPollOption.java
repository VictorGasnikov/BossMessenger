package net.pixelatedd3v.bossmessenger.service.feedback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfPollOption complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="ArrayOfPollOption">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PollOption" type="{http://pixelatedev.com/bossmessenger/feedback}PollOption" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPollOption", propOrder = {"pollOption"})
public class ArrayOfPollOption {

	@XmlElement(name = "PollOption", nillable = true)
	protected List<PollOption> pollOption;

	/**
	 * Gets the value of the pollOption property.
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the pollOption property.
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getPollOption().add(newItem);
	 * </pre>
	 * Objects of the following type(s) are allowed in the list
	 * {@link PollOption }
	 */
	public List<PollOption> getPollOption() {
		if (pollOption == null) {
			pollOption = new ArrayList<PollOption>();
		}
		return this.pollOption;
	}

}
