import React, {PropTypes, Component} from 'react';
import InputDescription from '../InputDescription/InputDescription';

import Styles from './styles.less';

export default class CreateInstance extends Component{
    constructor(props){
        super(props);
        this.state = {
            description: '',
            streamType: 'IM'
        }
    }

    onCancel() {
        //TODO back to list view
		//hashHistory.push('/list-view');
	}
    
    render(){
        return (
            <div className="container-component block">
                <InputDescription handleChange={description => this.setState({description: description})} />
                <div className="submit-webhook">
                    <div className="btn-container">
                        <button className="button" onClick={() => this.props.onCreate(this.state)} type="button" >Add</button>
                        <button className="button cancel-link" onClick={this.onCancel}>Cancel</button>
                    </div>
			    </div>
            </div>
        );
    }
}