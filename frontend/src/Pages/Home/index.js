import 'bootstrap/dist/css/bootstrap.css';
import './index.css';
import React, {useState} from 'react';
import Header from "../../Layouts/Header";
import TextInput from "../../Components/TextInput";
import UploadButton from "../../Components/UploadButton";
import ConvertButton from "../../Components/ConvertButton";
import TextVisualizer from "../../Components/TextVisualizer";

function Home() {
    const [textContent, setTextContent] = useState("");
    const [error, setError] = useState("");
    const [scl, setScl] = useState(null);

    const handleTextChange = (text) => {
        setTextContent(text);
    }

    const handleConvert = () => {
        fetch('http://localhost:8080/scl/parser', {
            method: 'POST',
            headers: {"Content-Type": "text/plain"},
            mode: 'cors',
            body: textContent
        }).then(response => response.json())
          .then(json => {
              setError(json.error);
              setScl(json.scl);
          });
    }

    return (
        <div className="h-100">
            <Header/>
            <div className="container my-4 h-75">
                <div className="row h-100">
                    <div className="col-5">
                        <TextInput onChange={e => handleTextChange(e.target.value)} value={textContent}/>
                    </div>
                    <div className="col-2">
                        <UploadButton handleTextChange={handleTextChange}/>
                        <ConvertButton handleConvert={handleConvert}/>
                    </div>
                    <div className="col-5 box">
                        <TextVisualizer scl={scl} error={error}/>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Home;