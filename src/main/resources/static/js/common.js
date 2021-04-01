let Commons = (function () {
    /**
     * 공통 ajax call 함수
     * @param requestUrl
     * @param callback
     * @private
     */
    let ajaxGet = function (requestUrl, callback) {
        $.ajax({
            type: 'GET',
            url: requestUrl,
            cache: false,
            // data: params,
            dataType: 'json',
            success: function(resp) {
               callback(resp);
            },
            error: function (jqXHR, status, error) {
               console.log(jqXHR);
               alert('ERROR [' + status + '] ' + error);
           }
        }).done(function() {
             // Commons.offOverlay();
        });
    };

    let ajaxPost = function (requestUrl, params, callback) {
        $.ajax({
            type: 'POST',
            url: requestUrl,
            data: JSON.stringify(params),
            contentType: 'application/json',
            dataType: 'json',
            success: function(resp) {
               callback(resp);
            },
            error: function (jqXHR, status, error) {
               console.log(jqXHR);
               alert('ERROR [' + status + '] ' + error);
           }
        });
    };

    let ajaxDelete = function (requestUrl, params, callback) {
        $.ajax({
            type: 'DELETE',
            url: requestUrl,
            data: JSON.stringify(params),
            contentType: 'application/json',
            dataType: 'json',
            success: function(resp) {
               callback(resp);
            },
            error: function (jqXHR, status, error) {
               console.log(jqXHR);
               alert('ERROR [' + status + '] ' + error);
           }
        });
    };

    let ajaxPut = function (requestUrl, params, callback) {
        $.ajax({
            type: 'PUT',
            url: requestUrl,
            data: JSON.stringify(params),
            contentType: 'application/json',
            dataType: 'json',
            success: function(resp) {
               callback(resp);
            },
            error: function (jqXHR, status, error) {
               console.log(jqXHR);
               alert('ERROR [' + status + '] ' + error);
           }
        });
    };

    let null2Space = function (obj) {
        return isEmpty(obj) ? '' : obj;
    };

    let isEmpty = function (obj) {
        if (typeof obj == 'undefined' || obj === null || obj === '') return true;
        if (typeof obj == 'number' && isNaN(obj)) return true;
        if (typeof obj == 'object' && Object.keys(obj).length === 0) return true;
        if (obj instanceof Date && isNaN(Number(obj))) return true;
        return false;
    };

    let isNotEmpty = function (obj) {
        return !isEmpty(obj);
    };

    let initCap = function (str) {
        str = str.substring(0, 1).toUpperCase() + str.substring(1, str.length).toLowerCase()
        return str;
    };
    // Unescape HTML in JS
    // https://stackoverflow.com/questions/1912501/unescape-html-entities-in-javascript
    let htmlDecode = function (input) {
        var e = document.createElement('textarea');
        e.innerHTML = input;
        // handle case of empty input
        return e.childNodes.length === 0 ? "" : e.childNodes[0].nodeValue;
    }

    let markingErrorField = function (response) {
        const errorFields = response.responseJSON.errors;

        if(!errorFields){
            alert(response.response.message);
            return;
        }

        let $field, error;
        for(let i=0, length = errorFields.length; i<length;i++){
            error = errorFields[i];
            $field = $('#'+error['field']);

            if($field && $field.length > 0){
                $field.siblings('.error-message').remove();
                $field.after('<span class="error-message text-muted taxt-small text-danger">'+error.defaultMessage+'</span>');
            }
        }
    };

    return {
        ajaxGet: ajaxGet,
        ajaxPost: ajaxPost,
        ajaxDelete: ajaxDelete,
        ajaxPut: ajaxPut,
        isEmpty: isEmpty,
        isNotEmpty: isNotEmpty,
        initCap: initCap,
        null2Space: null2Space,
        htmlDecode: htmlDecode
    }
})();