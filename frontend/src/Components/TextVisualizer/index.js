import React from 'react';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import TreeView from '@mui/lab/TreeView';
import TextNode from "../TextNode";

function TextVisualizer(props) {
    const scl = props.scl;
    const error = props.error;
    let id = 0;

    const idGen = () => {
        return id++;
    }

    if (error !== "") {
        return (
            <div>
                <span className="text-danger">An error occurred: {error}</span>
            </div>
        )
    }

    if (scl == null) {
        return;
    }

    return (
        <TreeView
            aria-label="text visualizer"
            defaultCollapseIcon={<ExpandMoreIcon />}
            defaultExpandIcon={<ChevronRightIcon />}
            sx={{ flexGrow: 1, overflowY: 'auto' }}
        >
            <TextNode node={scl} idGen={idGen}/>
        </TreeView>
    );
}

export default TextVisualizer;