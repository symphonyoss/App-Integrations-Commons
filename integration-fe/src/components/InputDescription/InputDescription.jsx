import React, {Component} from 'react';

import Styles from './styles.less';

export default class InputDescription extends Component{
    render(){
        return (
            <div className='input-description-container'>
                <h5><label htmlFor="ii-name">Description</label></h5>
                <div>
                    <input type="text" className="text-input" ref="instanceName" id="ii-name" placeholder="Add a short description here" onChange={event => this.props.handleChange(event.target.value)}/>
                </div>
            </div>
        );
    }
}