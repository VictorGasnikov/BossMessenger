package net.pixelatedd3v.bossmessenger.service.feedback;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for ArrayOfPoll complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType name="ArrayOfPoll">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Poll" type="{http://pixelatedev.com/bossmessenger/feedback}Poll" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfPoll", propOrder = {"poll"})
public class ArrayOfPoll {

	@XmlElement(name = "Poll", nillable = true)
	protected List<Poll> poll;

	/**
	 * Gets the value of the poll property.
	 * This accessor method returns a reference to the live list,
	 * not a snapshot. Therefore any modification you make to the
	 * returned list will be present inside the JAXB object.
	 * This is why there is not a <CODE>set</CODE> method for the poll property.
	 * For example, to add a new item, do as follows:
	 * <pre>
	 *    getPoll().add(newItem);
	 * </pre>
	 * Objects of the following type(s) are allowed in the list
	 * {@link Poll }
	 */
	public List<Poll> getPoll() {
		if (poll == null) {
			poll = new ArrayList<Poll>();
		}
		return this.poll;
	}

}
