package net.pixelatedd3v.bossmessenger.service.feedback;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.pixelatedev.bossmessenger.feedback package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {


	/**
	 * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.pixelatedev.bossmessenger.feedback
	 */
	public ObjectFactory() {
	}

	/**
	 * Create an instance of {@link GetAvailablePollsResponse }
	 */
	public GetAvailablePollsResponse createGetAvailablePollsResponse() {
		return new GetAvailablePollsResponse();
	}

	/**
	 * Create an instance of {@link ArrayOfPoll }
	 */
	public ArrayOfPoll createArrayOfPoll() {
		return new ArrayOfPoll();
	}

	/**
	 * Create an instance of {@link LoadPollResponse }
	 */
	public LoadPollResponse createLoadPollResponse() {
		return new LoadPollResponse();
	}

	/**
	 * Create an instance of {@link LoadedPoll }
	 */
	public LoadedPoll createLoadedPoll() {
		return new LoadedPoll();
	}

	/**
	 * Create an instance of {@link VoteMultipleChoiceResponse }
	 */
	public VoteMultipleChoiceResponse createVoteMultipleChoiceResponse() {
		return new VoteMultipleChoiceResponse();
	}

	/**
	 * Create an instance of {@link VoteMultipleChoice }
	 */
	public VoteMultipleChoice createVoteMultipleChoice() {
		return new VoteMultipleChoice();
	}

	/**
	 * Create an instance of {@link GetAvailablePolls }
	 */
	public GetAvailablePolls createGetAvailablePolls() {
		return new GetAvailablePolls();
	}

	/**
	 * Create an instance of {@link LoadPoll }
	 */
	public LoadPoll createLoadPoll() {
		return new LoadPoll();
	}

	/**
	 * Create an instance of {@link ArrayOfPollOption }
	 */
	public ArrayOfPollOption createArrayOfPollOption() {
		return new ArrayOfPollOption();
	}

	/**
	 * Create an instance of {@link Poll }
	 */
	public Poll createPoll() {
		return new Poll();
	}

	/**
	 * Create an instance of {@link PollOption }
	 */
	public PollOption createPollOption() {
		return new PollOption();
	}

}
