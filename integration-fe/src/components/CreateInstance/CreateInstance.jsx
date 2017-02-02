import React, {PropTypes} from 'react';

const CreateInstance = ({myValue, myOnClick}) => {
    return (
        <div>
            <p>testando -> {myValue}</p>
            <div onClick={() => myOnClick('hello integration')} >click</div>
        </div>
    )
}

CreateInstance.propTypes = {
    myValue: PropTypes.string,
    myOnClick: PropTypes.func
}

export default CreateInstance;
