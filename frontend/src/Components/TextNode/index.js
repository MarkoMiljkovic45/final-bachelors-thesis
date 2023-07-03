import React from 'react';
import TreeItem from '@mui/lab/TreeItem';

function TextNode(props) {
    const node = props.node;
    let idGen = props.idGen;

    let args;
    let value;
    let children;

    if (Object.keys(node.args).length !== 0) {
        args = Object.keys(node.args).map(key => (
            <TreeItem nodeId={idGen()} label={key + ": " + node.args[key]}/>
        ));
    } else {
        args = null;
    }

    if (node.value !== "") {
        value = <TreeItem nodeId={idGen()} label={node.value}/>;
    } else {
        value = null;
    }

    if (node.children.length !== 0) {
        children = node.children.map(child => (
            <TextNode node={child} idGen={idGen}/>
        ));
    } else {
        children = null;
    }

    if (args === null && value === null && children === null) {
        return <TreeItem nodeId={idGen()} label={node.name}/>
    } else {
        return (
            <TreeItem nodeId={idGen()} label={node.name}>
                { args }
                { value }
                { children }
            </TreeItem>
        )
    }
}

export default TextNode;