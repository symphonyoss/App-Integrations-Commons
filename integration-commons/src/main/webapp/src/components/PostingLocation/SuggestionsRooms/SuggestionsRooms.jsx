import React, { PropTypes, Component } from 'react';
import RoomBox from '../RoomBox/RoomBox';
import './styles/styles.less';

// Use named export for unconnected component (for tests)
export class SuggestionsRooms extends Component {
  constructor(props) {
    super(props);

    this.state = {
      filters: [],          // stores the room filtered
      filteredRooms: [],    // rooms that appears according to user typing
      suggestionsList: [],  // rooms available to be filtered
      listening: false,     // check if input search has event listener
      focused: -1,          // handles list items focus
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
    this.sort = this.sort.bind(this);
  }

  componentWillMount() {
    const _suggestions = this.props.userRooms.slice();
    this.sort(_suggestions, 'name');
    this.setState({
      suggestionsList: _suggestions,
    });
  }

  componentDidMount() {
    this.input.focus();
  }

  onChangeSearch(e) {
    const target = e.target;
    if (target.value === '') {
      this.setState({
        filteredRooms: [],
      });
      return;
    }
    let suggestionsList = this.state.suggestionsList;
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

  addFilter(e, filter) {
    e.preventDefault();
    const suggestions = this.state.suggestionsList.slice();
    suggestions.some((item, idx) => {
      if (item.name === filter.name) {
        suggestions.splice(idx, 1);
      }
    });

    this.setState({
      filteredRooms: [],
      focused: -1,
      filters: this.state.filters.concat([filter]),
      suggestionsList: suggestions.slice(),
    });

    this.props.addStreamToInstance(filter.threadId);
    this.input.value = '';
    this.input.focus();
  }

  removeFilter(_id) {
    const _suggestions = this.state.suggestionsList.slice();
    const _filters = this.state.filters.slice();

    _filters.some((item, idx) => {
      if (item.threadId === _id) {
        _suggestions.push(item);
        _filters.splice(idx, 1);
      }
    });

    this.sort(_suggestions, 'name');
    this.input.value = '';
    this.input.focus();
    this.setState({
      suggestionsList: _suggestions.slice(),
      filters: _filters.slice(),
    });

    this.props.removeStreamFromInstance(_id);
  }

  sort(_obj, key) {
    _obj.sort((a, b) => {
      if (a[key] < b[key]) return -1;
      if (a[key] > b[key]) return 1;
      return 0;
    });
  }

  render() {
    return (
      <div className='suggestions-rooms'>
        <div className='input-search-container'>
          <input
            type='text'
            onChange={this.onChangeSearch}
            ref={(input) => { this.input = input; }}
            // placeholder={this.props.loading ? 'Loading...' : 'Search rooms'}
            placeholder='Search rooms'
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
                  onClick={e => this.addFilter(e, filter, idx)}
                  id={idx}
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
        { this.state.filters.length > 0 &&
          (
            this.state.filters.map((room, idx) =>
              <RoomBox
                key={idx}
                name={room.name}
                threadId={room.threadId}
                public={room.publicRoom}
                memberCount={room.memberCount}
                creatorPrettyName={room.creatorPrettyName}
                removeFilter={this.removeFilter}
              />
          )
        )}
      </div>
    );
  }
}

SuggestionsRooms.propTypes = {
  addStreamToInstance: PropTypes.func.isRequired,
  removeStreamFromInstance: PropTypes.func.isRequired,
  userRooms: PropTypes.arrayOf(PropTypes.object).isRequired,
};

export default SuggestionsRooms;
