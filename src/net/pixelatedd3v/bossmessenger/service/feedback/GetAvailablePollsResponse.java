package net.pixelatedd3v.bossmessenger.service.feedback;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for anonymous complex type.
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="GetAvailablePollsResult" type="{http://pixelatedev.com/bossmessenger/feedback}ArrayOfPoll" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"getAvailablePollsResult"})
@XmlRootElement(name = "GetAvailablePollsResponse")
public class GetAvailablePollsResponse {

	@XmlElement(name = "GetAvailablePollsResult")
	protected ArrayOfPoll getAvailablePollsResult;

	/**
	 * Gets the value of the getAvailablePollsResult property.
	 *
	 * @return possible object is
	 * {@link ArrayOfPoll }
	 */
	public ArrayOfPoll getGetAvailablePollsResult() {
		return getAvailablePollsResult;
	}

	/**
	 * Sets the value of the getAvailablePollsResult property.
	 *
	 * @param value allowed object is
	 *              {@link ArrayOfPoll }
	 */
	public void setGetAvailablePollsResult(ArrayOfPoll value) {
		this.getAvailablePollsResult = value;
	}

}
