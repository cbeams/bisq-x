package bisq.core.network.http;

import bisq.core.logging.Logging;

import org.slf4j.Logger;

interface HttpLog {

    String HTTP_LOG_NAME = "http";

    Logger log = Logging.getLog(HTTP_LOG_NAME);
}
