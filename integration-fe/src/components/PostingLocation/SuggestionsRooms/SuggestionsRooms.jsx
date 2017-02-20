import React, { PropTypes, Component } from 'react';
import './styles/styles.less';

// Use named export for unconnected component (for tests)
export class SuggestionsRooms extends Component {
  constructor(props) {
    super(props);

    this.state = {
      filters: [],
      filteredRooms: [],
      listening: false, // check if input search has event listener
      focused: -1,
    };
    this.container = null;
    this.input = null;
    this.list = null;
    this.onChangeSearch = this.onChangeSearch.bind(this);
    this.inputListener = this.inputListener.bind(this);
    this.addInputListener = this.addInputListener.bind(this);
    this.removeInputListener = this.removeInputListener.bind(this);
    this.clearInput = this.clearInput.bind(this);
    this.addFilter = this.addFilter.bind(this);
    this.removeFilter = this.removeFilter.bind(this);
  }

  componentWillMount() {
    if (this.props.userRooms.length === 0) {
      this.props.fetchUserRooms();
    }
  }

  componentDidMount() {
    const tmr = setInterval(() => {
      if (!this.props.loading) {
        clearInterval(tmr);
        this.input.focus();
      }
    }, 500);
  }

  onChangeSearch(e) {
    const target = e.target;
    if (target.value === '') {
      this.setState({
        filteredRooms: [],
      });
      return;
    }
    let suggestionsList = this.props.userRooms.slice();
    suggestionsList = suggestionsList.filter(item =>
      item.name.toLowerCase().search(e.target.value.toLowerCase()) !== -1
    );
    if (target.value !== '') {
      if (!this.state.listening) {
        this.addInputListener();
      }
    } else if (target.value === '') {
      if (this.state.listening) {
        this.removeInputListener();
      }
    }
    this.setState({
      filteredRooms: suggestionsList,
    });
  }

  inputListener(event) {
    let idx = this.state.focused;
    const key = event.keyCode;

    // limit case 1: Focus is on input and key is arrow up: return...
    if (idx === -1 && key === 38) {
      setTimeout(() => {
        this.input.value = this.input.value;
        this.input.focus();
      }, 10);
      return;
    }

    // limit case 2: Focus is on the last list item, and key is arrow down: return...
    if (idx === this.state.filteredRooms.length - 1 && key === 40) {
      return;
    }

    // Focus is on input and key is arrow down
    if (idx === -1 && key === 40) {
      this.input.blur();
      this.removeInputListener();
      this.list.addEventListener('keydown', this.inputListener);
      idx += 1;
    } else if (idx === 0) { // Focus is on the first list item
      // key is arrow up
      if (key === 38) {
        // setInterval put the cursor after the input value...
        const tmr = setInterval(() => {
          if (this.input.value !== '') {
            clearInterval(tmr);
            this.input.focus();
          }
        }, 50);
        this.list.removeEventListener('keydown', this.inputListener);
        this.addInputListener();
        idx -= 1;
      } else if (key === 40) { // key is arrow down
        idx += 1;
      }
    } else if (idx > 0) { // Focus is on any list item, except the first and last...
      if (key === 38) {
        idx -= 1;
      } else if (key === 40) {
        idx += 1;
      }
    }

    // Handles focus change
    if (this.state.filteredRooms.length > 0 && idx >= 0) {
      this.list.childNodes[idx].childNodes[0].focus();
    }

    // Update state
    this.setState({
      focused: idx,
    });
  }

  addInputListener() {
    this.input.addEventListener('keydown', this.inputListener);
    this.setState({
      listening: true,
    });
  }

  removeInputListener() {
    this.input.removeEventListener('keydown', this.inputListener);
    this.setState({
      listening: false,
    });
  }

  clearInput() {
    this.setState({
      filteredRooms: [],
      focused: -1,
    });
    this.input.value = '';
    this.input.focus();
  }

  addFilter() {

  }

  removeFilter() {

  }

  render() {
    return (
      <div className='suggestions-rooms'>
        <div className='input-search-container'>
          <input
            type='text'
            onChange={this.onChangeSearch}
            ref={(input) => { this.input = input; }}
            placeholder={this.props.loading ? 'Loading...' : 'Search rooms'}
          />
          <button onClick={this.clearInput}>
            <i className='fa fa-times' />
          </button>
        </div>
        <ul
          className='filter-container'
          ref={(list) => { this.list = list; }}
        >
          {
            this.state.filteredRooms.map((filter, idx) =>
              <li className='filter-box' key={idx}>
                <a
                  href='#'
                  className='filter-box-clickable'
                >
                  <div>
                    <span>{filter.name}</span>
                    {!filter.publicRoom && (<span><i className="fa fa-lock" /></span>)}
                  </div>
                  <div className='room-info'>
                    <span>
                      {
                        `${filter.memberCount} Member${filter.memberCount > 1 ? 's' : ''},
                         created by ${filter.creatorPrettyName}`
                      }
                    </span>
                  </div>
                </a>
              </li>
            )
          }
        </ul>
      </div>
    );
  }
}

SuggestionsRooms.propTypes = {
  fetchUserRooms: PropTypes.func.isRequired,
  userRooms: PropTypes.arrayOf(PropTypes.object).isRequired,
  loading: PropTypes.bool.isRequired,
};

export default SuggestionsRooms;
