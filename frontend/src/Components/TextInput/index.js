import 'bootstrap/dist/css/bootstrap.css';
import './index.css';
import '../../Assets/Styles/default.css';
import React from 'react';

function TextInput(props) {
    return (
        <form className="h-100">
            <div className="form-group h-100">
                <textarea
                    onChange={props.onChange}
                    value={props.value}
                ></textarea>
            </div>
        </form>
    )
}

export default TextInput;