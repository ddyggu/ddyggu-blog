/**
 * 
 */

var Observe = function(oEl) {

	this._o = oEl;
	this._value = oEl.value;
	this._bindEvents();
};

Observe.prototype._bindEvents = function() {
	var self = this;
	var bind = function(oEl, sEvent, pHandler) {
	if (oEl.attachEvent) oEl.attachEvent('on' + sEvent, pHandler);
	else oEl.addEventListener(sEvent, pHandler, false);
};

	bind(this._o, 'focus', function() {
	if (self._timer) clearInterval(self._timer);
	self._timer = setInterval(function() {
		if (self._value != self._o.value) {
			self._value = self._o.value;
			self._fireEvent();
		}
	}, 500);

   });

	bind(this._o, 'blur', function() {
	if (self._timer) clearInterval(self._timer);
		self._timer = null;
	});
};

 
Observe.prototype._fireEvent = function() {
	if (document.createEvent) {
		var e;

	if (window.KeyEvent) {
		e = document.createEvent('KeyEvents');
		e.initKeyEvent('keyup', true, true, window, false, false, false, false, 65, 0);
	} else {
		e = document.createEvent('UIEvents');
		e.initUIEvent('keyup', true, true, window, 1);
		e.keyCode = 65;
	}

	this._o.dispatchEvent(e);

	} else {
		var e = document.createEventObject();
		e.keyCode = 65;
		this._o.fireEvent('onkeyup', e);
	}

}; 



 function addInputHandler(conditions) {
            var $input = conditions.input;
            var dataType = conditions.dataType;
            var eventType = conditions.eventType;

            if ((!$input) || (!dataType)) {
                throw { error: "NotEnoughArguments", errorMsg: "required argument is missing " + ((!$input) ? " target input element" : " dataType") }
                return;
            }

            if ($input[0].tagName != "INPUT") {
            	throw { error: "IlregalTargetElement", errorMsg: "target element is not input" };
            	return;
            }

            if ((!eventType)) {
            	eventType = "keyup";
            }

            var handlerFunc = conditions.handler;

            if ((!handlerFunc)) {

            	handlerFunc = function (event) {

            		var regEx = null;

                    if (dataType == "N") {
                    	// 숫자가 아닌 경우 검증
                    	regEx = /[^0-9]/gi;
                    } else if (dataType == "AP") {
                    	// 알파벳이 아닌 경우 검증
                        regEx = /[^a-z]/gi;
                    } else if (dataType == "AN") {
                    	// 숫자, 알파벳이 아닌 경우 검증
                        regEx = /[^a-z0-9]/gi;
                    } else if (dataType == "ANS") {
                    	// 숫자, 알파벳, 일부 특수기호가 아닌 경우 검증
                        regEx = /[^a-z0-9\@\.]/gi;
                    } else if (dataType == "HA") {
                    	// 한글이 아닌 경우 검증
                        regEx = /[a-z0-9]/gi;
                    } else {
                        throw { error: "IlregalDataType", errorMsg: "dataType(" + dataType + ") is incorrect" }
                    }

                    remainOnlyTargetValue(regEx, $input, event);
                    //return true;

                };  // end of handlerFunc

            } // end of if to check handlerFunc

            $input.on(eventType, handlerFunc);

            if (conditions.maxlength) {
            	$input.attr("maxlength", conditions.maxlength);
            }
}

function remainOnlyTargetValue(regEx, $input, event) {
       if ((!(event.keyCode >= 34 && event.keyCode <= 40)) && event.keyCode != 16) {
        	var inputVal = $input.val();
        	if (regEx.test(inputVal)) {
        		$input.val(inputVal.replace(regEx, ''));
        	}
       }
 }