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

import java.util.logging.Logger;

public class CoreException extends Exception {

    private static final Logger logger = Logger.getLogger(CoreException.class.getName());

    private final com.example.simplesaletransection.network.CoreExceptionType exceptionType;

    public CoreException(com.example.simplesaletransection.network.CoreExceptionType exceptionType) {
        super( exceptionType.getErrorMsg() );
        this.exceptionType = exceptionType;
        logger.severe("CoreException" + exceptionType.getErrorMsg() );
        this.printStackTrace();
    }

    public com.example.simplesaletransection.network.CoreExceptionType getExceptionType() {
        return exceptionType;
    }

}
