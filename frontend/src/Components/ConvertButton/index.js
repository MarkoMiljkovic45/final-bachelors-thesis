import './index.css';
import React from 'react';
import {Button} from "@mui/material";
import ForwardIcon from '@mui/icons-material/Forward';

const ConvertButton = (props) => {
    const handleConvert = props.handleConvert;

    return (
        <div style={{margin: 8}}>
            <Button variant="contained"
                    color="inherit"
                    className="button"
                    fullWidth={true}
                    startIcon={<ForwardIcon/>}
                    onClick={handleConvert}
            >
                <span className="buttonText">Convert</span>
            </Button>
        </div>
    );
};

export default ConvertButton;
