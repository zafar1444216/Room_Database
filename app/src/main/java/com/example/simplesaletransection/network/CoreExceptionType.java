/*
 *  Copyright (c) 2020, Girmiti Software Pvt. Ltd. and/or its
 *  affiliates. All rights reserved.
 *
 *  Please refer to the file LICENSE.TXT for full details.
 *
 *  TO THE EXTENT PERMITTED BY LAW, THE SOFTWARE IS PROVIDED "AS IS", WITHOUT
 *  WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NON INFRINGEMENT. TO THE EXTENT PERMITTED BY LAW, IN NO EVENT SHALL
 *  MASTERCARD OR ITS AFFILIATES BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *  IN THE SOFTWARE.
 */
package com.example.simplesaletransection.network;

public enum CoreExceptionType {

    NONE(""),
    SERVICE_NOT_EXIST_EXCEPTION("Bind Service Failed"),
    SERVICE_REMOTE_EXCEPTION("Service Exception"),
    SERVICE_DEVICE_EXCEPTION("Get Device Exception"),

    CHECKCARD_CANCELLED_EXCEPTION("Transaction Cancelled"),
    CHECKCARD_READ_EXCEPTION("CardFragment Read Exception"),
    CHECKCARD_EXPIRED_EXCEPTION("Expired CardFragment"),
    CHECKCARD_TRACKINFO_EXCEPTION("Track Info Exception"),
    CHECKCARD_VOID_DIFF_CARDNO_EXCEPTION("No match to original card"),
    CHECKCARD_NOT_SUPPORT_EXCEPTION("This card is not supported"),
    CHECKCARD_NO_HOST_EXCEPTION("This card no exception"),
    CHECKCARD_NOT_MANUAL_EXCEPTION("This card is not supported"),
    CHECKCARD_NOT_ENABLE_EXCEPTION("This card not support requested transaction"),
    CHECKCARD_ERROR_IC_CARD_EXCEPTION("Please remove card"),

    EMV_CONTACT_CARD_TRANS_EXCEPTION("EMV Contact CardFragment Transaction Exception"),
    EMV_CONTACTLESS_CARD_TRANS_EXCEPTION("EMV Contactless CardFragment Transaction Exception"),
    EMV_PROCESS_ONLINE_EXCEPTION("EMV Process Online Exception"),

    PINPAD_NO_SUPPORT_DES_EXCEPTION("No support for single DES"),
    PINPAD_DOWNLOAD_KEY_EXCEPTION("Key download exception"),
    PINPAD_INPUTPIN_OTHER_EXCEPTION("Input PIN exception"),
    PINPAD_CALC_MAC_EXCEPTION("Calculate MAC exception"),
    PINPAD_CHECK_MAC_EXCEPTION("Verify MAC exception"),

    PRINT_PARAM_IS_NULL_EXCEPTION("Print param is null"),
    PRINT_RECEIPT_EXCEPTION("Error in printing receipt"),

    PBOC_TRANSACTION_EXCEPTION("Transaction rejected"),
    PBOC_ARPC_CHECK_EXCEPTION("ARPC verify exception"),

    PRINT_ACTION_EXCEPTION("Print exception"),
    SCAN_ERROR_EXCEPTION("Scan exception"),

    DATABASE_SYSTRACE_EXCEPTION("Record database exception"),
    DATABASE_REVERSAL_EXCEPTION("Reversal database exception"),
    DATABASE_NOT_FOUND_EXCEPTION("No record"),
    DATABASE_RECORD_INSERT_EXCEPTION("Insert record fail"),

    NETWORK_SOCKET_EXCEPTION("Socket failed"),
    NETWORK_SEND_EXCEPTION("Send data failed"),
    NETWORK_RECEIVE_EXCEPTION("No Response"),
    NETWORK_TIMEOUT_EXCEPTION("Receive data timeout"),
    NETWORK_SSL_EXCEPTION("Load SSL certificate failed"),
    NETWORK_DISCONNECT_EXCEPTION("Network disconnected"),
    NETWORK_INPUT_PARAM_EXCEPTION("Network param illegal"),
    USB_CONNECT_EXCEPTION("USB connect failed"),
    KEY_INJECTION_EXCEPTION("Key injection failed"),

    ISO8583_BUILD_EXCEPTION(""),
    ISO8583_SETTLEFRAME_EXCEPTION(""),
    ISO8583_SETTLEFRAME_FIELD39_EXCEPTION(""),
    ISO8583_SETTLEFRAME_NOPARAM_EXCEPTION(""),

    ICCARD_READER_KEY_EXCEPTION(""),
    SERIAL_PORT_EXCEPTION(""),

    CONFIG_FILE_EXCEPTION("APP Init Param Exception  ERROR CODE:1"),
    CONFIG_LIST_NULL_EXCEPTION("APP Init Param Exception  ERROR CODE:2"),
    CONFIG_BEAN_NULL_EXCEPTION("APP Init Param Exception  ERROR CODE:3"),
    CONFIG_BEANATTRIBUTES_NULL_EXCEPTION("APP Init Param Exception  ERROR CODE:4"),
    CONFIG_LIST_ZERO_EXCEPTION("APP Init Param Exception  ERROR CODE:5"),

    TRANSTYPE_IS_EXCEPTION("Trans Type Exception"),
    OPERATOR_TIMEOUT_EXCEPTION("Operate Timeout"),
    OPERATOR_CANCEL_EXCEPTION("Operation is Canceled"),
    OTHER_ERROR_EXCEPTION("Others Exception"),

    TRANS_NOT_ALLOWED("ERROR_TRANS_NOT_ALLOWED"),
    NETWORK_TRANS_COMPLETED("NETWORK_TRANS_COMPLETED"),

    GENERAL_ERROR_OBJECT_NULL("Object is null"),
    PACK_OBJECT_ERROR("Transaction Declined"),

    ISO_PACK_ERROR("Error while packing ISO request"),
    ISO_UNPACK_ERROR("Error while unpacking ISO response"),

    INVALID_CARD("Invalid CardFragment, Luhn check failed"),
    CONTACTLESS_DISABLED("Contactless Disabled"),
    CARD_NOT_SUPPORTED("CardFragment Not Supported"),
    CARD_RESTRICTED("CardFragment Restricted"),
    CARD_FUNCTION_DISABLED("CardFragment Function Disabled"),
    TXN_LIMIT_EXCEEDED("Transaction Limit Exceeded"),
    TXN_LIMIT_BELOW("Transaction Limit Below Minimum Amount"),
    INVALID_SERVICE_CODE("Invalid Service Code"),

    PIN_OPERATION_CANCELLED("PIN Operation cancelled"),
    PIN_INPUT_EXCEPTION("PIN Input Exception"),
    PIN_PLAIN_TEXT_NULL_EXCEPTION("PIN Plain Text null"),
    PIN_ENCRYPT_EXCEPTION("PIN Encrypt Exception"),
    PIN_CIPHER_TEXT_EXCEPTION("PIN Cipher Text Exception"),
    PIN_OTHER_EXCEPTION("PIN Entry Other Exception"),
    PIN_TIMEOUT_EXCEPTION("PIN Timeout Exception"),
    INVALID_STAN("Invalid Receipt# or no matching record found"),
    VOIDED_STAN("Already voided"),
    IMPORT_APPLICATION_SELECTION_EXCEPTION("Import App selection Exception"),
    NO_RECORD("No record found"),
    EMPTY_BATCH("Empty Batch"),
    EMPTY_REVERSAL("No Reversal Present"),
    BATCH_RECORD_AVIALBLE("Batch is not empty, Download parameters not allowed"),
    BATCH_NOT_EMPTY("Batch is not empty, Please Settle Batch"),
    LAST_EMV_TXN_EMPTY("EMV transaction not available"),
    SYNC_DATE_EXCEPTION("Sync Date Failed"),
    FALLBACL_NOT_ALLOWED_EXCEPTION("Fall Back Not Allowed"),
    LOGON_EXCEPTION("Logon exception");

    private int errorCode;
    private String errorMsg;

    public int getErrorCode() {
        return errorCode;
    }

    public CoreExceptionType setErrorCode(int errorCode) {
        this.errorCode = errorCode;
        return this;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public CoreExceptionType setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
        return this;
    }

    CoreExceptionType(String errorMsg) {
        this.errorMsg = errorMsg;
    }

}
