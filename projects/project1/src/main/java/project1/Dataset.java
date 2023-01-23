package project1;

/* Team needs to import relevant packages here */

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.nio.file.Paths;

/**
 * Dataset
 * @author tesic
 * @author toufik
 */
public class Dataset {

	// Constructors

	/**
	 * Default constructor
	 */
	public Dataset() throws IOException{
		this("", Paths.get(""));
	}

	/**
	 * Dataset constructor w 2 parameters
	 * @param dataId unique dataset identifier
	 * @param inRawFile  Amazon rating input file
	 */
	public Dataset(String dataId, Path inRawFile) throws IOException {
		this.dataId = dataId;
		this.rawFile = inRawFile;
		this.numberOfRatings = Files.lines(inRawFile).count();
		this.ratingList = new ArrayList<>();
		this.ratingStat = new ArrayList<>();
	}

	/**
	 * Implement readRatings method
	 * Add javadoc
	 */
	public int readRatings() {

		//your code here 

	}

	/**
	 * Implement readStats method
	 * Add javadoc
	 */
	public int readStats(Path inStatPath) {

		//your code here

	}

	/**
	 * Compute Statistics for object ratings
	 * @return if computing statistics from raw file was a success
	 */
	public boolean computeStats() {
		// do not append, start from scratch
		this.ratingStat = new ArrayList<>();

		// create unique list of users and products here
		Set<String> nodeIds = this.ratingList.stream().map(r -> r.getReviewerID()).collect(Collectors.toSet());
		boolean newStats = nodeIds.addAll(this.ratingList.stream().map(r -> r.getProductID()).collect(Collectors.toSet()));

		// Create List from set
		List<String> uniqueList = nodeIds.stream().collect(Collectors.toCollection(ArrayList::new));

		// loop over field
		for (String user : uniqueList) {
			AbstractRatingSummary newSummary = new RatingSummary(user, this.ratingList);
			newStats = this.ratingStat.add(newSummary);
		}
		return newStats;
	}


	/**
	 * Compute Statistics for object ratings
	 * @return if computing statistics from raw file was a success
	 */
	public String saveStats() {

		StringBuilder statString = new StringBuilder();
		// writing a rating summary in each line
		for (AbstractRatingSummary rs : this.getRatingStat()) {
			statString.append(rs.toString());
		}
		return statString.toString();
	}

	/**
	 * Data ID getter
	 * @return  unique dataset identifier
	 */
	public String getDataId() {
		return dataId;
	}

	/**
	 * Data ID setter
	 * @param  dataId set unique dataset identifier
	 */
	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	/**
	 * Get ratings filename
	 * @return Path to ratings file
	 */
	public Path getRawFile() {
		return this.rawFile;
	}

	/**
	 * rating list getter
	 * @return list of ratings
	 */
	public List<Rating> getRatingList() {
		return this.ratingList;
	}

	/**
	 * stat list getter
	 * @return list of rating stats
	 */
	public List<AbstractRatingSummary> getRatingStat() {
		return this.ratingStat;
	}

	/**
	 * @return if the object has stats
	 */
	public boolean hasStats(){
		return !(this.ratingStat.isEmpty());
	}

	/**
	 * Rating stat list setter
	 * @param ratingSummary list of rating stats
	 */
	public void setRatingSummary(List<AbstractRatingSummary> ratingSummary) {
		this.ratingStat = ratingSummary;
	}

	/** Print out format is dataID,RAW_FILE,RATINGS_NO,STAT_FILE 
	 * @return formatted output 
	*/
	@Override
	public String toString() {
		return (this.dataId + "," + this.numberOfRatings + "," + this.rawFile.getFileName());
	}

	private String dataId;
	private Path rawFile;
	private long numberOfRatings;
	private List<Rating> ratingList;
	private List<AbstractRatingSummary> ratingStat;
}
