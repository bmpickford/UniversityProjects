function validateLogin(loginForm) {
	if (document.loginForm.email.value === "" || document.loginForm.password.value === "") {
		return false;
	}
	return true;
}

/** Registration Validation **/


function validateFName(regForm) {
    var name = document.regForm.fName.value;
    name = name.trim();
    if (name === "" || !isNaN(name) || name===null) {
        alert("Not a valid name");
        return false;
    }
    return true;
}

function validateSName(regForm) {
    var name = document.regForm.sName.value;
    name = name.trim();
    if (name === "" || !isNaN(name)) {
        alert("Not a valid name");
        return false;
    }
    return true;
}

function validatePWord(regForm) {
    if (document.regForm.password1.value === document.regForm.password2.value) { 
        return true;
    }
    return false;
}

function validateReg(regForm) {
    validateFName(regForm);
    validateSName(regForm);
    validatePWord(regForm);
}


/** END Registration Validation **/
