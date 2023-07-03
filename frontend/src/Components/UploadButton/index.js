import './index.css';
import React from 'react';
import {Button} from "@mui/material";
import FileUploadIcon from '@mui/icons-material/FileUpload';

const UploadButton = (props) => {
    const handleTextChange = props.handleTextChange;
    let fileReader;

    const handleFileRead = () => {
        const content = fileReader.result;
        handleTextChange(content);
    }

    const handleFileSelect = (file) => {
        if (file == null) return;
        fileReader = new FileReader();
        fileReader.onloadend = handleFileRead;
        fileReader.readAsText(file);
    };

    return (
        <div style={{margin: 8}}>
            <Button variant="contained"
                    component="label"
                    color="inherit"
                    className="button"
                    fullWidth={true}
                    startIcon={<FileUploadIcon/>}>
                <span className="buttonText">File</span>
                <input type="file"
                       style={{ display: 'none' }}
                       accept=".icd, .cid, .ssd, .scd, .iid, .sed"
                       onChange={e => handleFileSelect(e.target.files[0])}
                />
            </Button>
        </div>
    );
};

export default UploadButton;
