package zolostays.in.zolostays;

/**
 * Created by gulshan on 15/07/17.
 */

public interface Status {


    String USER_ID_PASSWORD_ARE_NOT_KNOWN = "2102";
    String USER_ID_NOT_KNOWN = "2103";
    String PASSWORD_IS_NOT_KNOWN = "2104";
    String USER_ID_OR_PASSWORD_INVALID = "401";
    String WARNING_EMAIL = "Please enter Username";
    String WARNING_EMAIL_INVALID = "Please enter valid Username";
    String WARNING_PASSWORD_INVALID = "Please enter valid Password";
    String WARNING_PASSWORD = "Please enter your Password";
    String UNKNOWN_ERROR = "Network error occured in Application. Please try again.";
    String INVALID_LOGIN_CREDENTIALS = "Invalid username or password.";
    String UNABLE_TO_CREATE_THE_FOLDER = "Unable to create folder";
    String DOWNLOADING_FILES = "Downloading Files ...";
    String NO_DESCRIPTION_MESSAGE = "Description not found";
    String NO_TECH_SPECH_MESSAGE = "Specification not found";
    String NO_TALK_POINTS_MESSAGE = "Talking points not found";
    String NO_DEMOGRAPHIC_KYC_MESSAGE = "Demographic KYC not found.";
    String NO_FEATURES_MESSAGE = "Details not found";
    String SESSION_OPEN = "Session has opened";
    String WIFI_OFF = "Please turn off your Wi-Fi or Mobile Data for continue.";
    String PUSH_NNOTIFICATION = "Unable to register for notification.Please check your Internet Connectivity and try again.";
    String LOCATION_SERVICES_TITLE = "Location services disabled";
    String LOCATION_SERVICES_MESSAGE = "Please enable location service to start/end the pitch.";
    String TRAINING_LOCATION_SERVICES_MESSAGE = "Please enable the location service to start training";
    String TRAINING_CERTIFICATE_SEND_MESSAGE = "Certificate successfully mailed to registered mail Id.";
    String ATTENDANCE_LOCATION_SERVICE_MESSAGE = "Please enable the location service to capture your attendance location.";

    String CONTINUE = "100";
    String CONTINUE_MESSAGE = "Continue";
    String SWITCHING_PROTOCOLS = "101";
    String SWITCHING_PROTOCOLS_MESSAGE = "Switching Protocols";
    String SUCCESS = "200";
    String FORBIDDEN = "401";
    String UNAUTHORIZED = "403";
    String NETWORK_ERROR = "No internet connectivity. Please try again";
    String CONNECTION_ISSUE = "Unable to connect to the server. Please try later.";
    String NO_INTERNET = "No internet connection.";
    String DOWNLOAD_PDF_AND_VIDEO = "Please connect to the Wi-Fi or Mobile Data network to download the file.";
    String FIRST_TIME_LOAD_MESSAGE = "Please connect to Wi-Fi or Mobile Data for first time data download";
    String NOT_DOWNLOADED = "Please connect to Wi- Fi or Mobile data for first time download the detials of product.";

    String SUCCESS_REGISTRATION = "Successfully Registered..!!";
    String SUCCESS_MESSAGE = "Data has been uploaded successfully.";
    String SUCCESS_SAVE = "Data has been saved and will be uploaded when connection is available.";
    String FAILED_MESSAGE = "Unable to upload the data. Please check your connectivity or try again later.";
    String SUCCESS_SAVED = "Data has been saved successfully.";
    String FAILED_SAVED = "Data has been failed. Please try again.";

    String UPLOAD_FAILED = "Data upload has been failed. Please try again.";
    String DOWNLOAD_FAILED = "File download has been failed. Please check your connectivity or try again later.";

    String SURGERY = "No. surgery can't be empty.";
    String REVENUE_POTENTIAL = "NR/foils can't be empty.";

    String SUCCESS_TITLE = "Success";
    String FAILED_TITLE = "Failed";

    String FILE_DOWNLOADING_MESSAGE = "Please wait. File is downloading.";
    String DOWNLOADED_MESSAGE= "File downloaded successfully";

    // String 200 => 'OK',
    // String 201 => 'Created',
    // String 202 => 'Accepted',
    String NON_AUTHORITATIVE_INFORMATION = "203";
    String WARNING_MOBILE_INVALID = "Please enter 10 digit mobile number";
    String TEMPORARILY_BLOCK = "406";
    String TEMPORARILY_BLOCK_MESSAGE = "Maximum login attempt reached.Account is temporarily blocked.";

}
