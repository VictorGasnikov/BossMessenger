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
 *         &lt;element name="LoadPollResult" type="{http://pixelatedev.com/bossmessenger/feedback}LoadedPoll" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {"loadPollResult"})
@XmlRootElement(name = "LoadPollResponse")
public class LoadPollResponse {

	@XmlElement(name = "LoadPollResult")
	protected LoadedPoll loadPollResult;

	/**
	 * Gets the value of the loadPollResult property.
	 *
	 * @return possible object is
	 * {@link LoadedPoll }
	 */
	public LoadedPoll getLoadPollResult() {
		return loadPollResult;
	}

	/**
	 * Sets the value of the loadPollResult property.
	 *
	 * @param value allowed object is
	 *              {@link LoadedPoll }
	 */
	public void setLoadPollResult(LoadedPoll value) {
		this.loadPollResult = value;
	}

}
