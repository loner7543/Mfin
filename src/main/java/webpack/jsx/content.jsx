const React = require('react')
const ReactDOM = require('react-dom')

class Content extends React.Component {
  constructor(props) {
    super(props)
    this.submit = this.submit.bind(this)
  }
  submit(event) {
    let emailAddress = this.refs.emailAddress
    let comments = this.refs.comments
    console.log(ReactDOM.findDOMNode(emailAddress).value)
    console.log(ReactDOM.findDOMNode(comments).value)
  }
  render() {
    this.prompt = 'Please enter your email'
    return (
      <div className="well">
        <p>{this.prompt}</p>
        <div className="form-group">
          fg: <input ref="emailAddress" className="form-control" type="text" placeholder="Email"/>
        </div>
        <div className="form-group">
          qwe: <textarea ref="comments" className="form-control"  placeholder="Put here some text"/>
        </div>
        <div className="form-group">
          <a className="btn btn-primary" onClick={this.submit}>Submit</a>
        </div>
      </div>
    )
  }
}

module.exports = Content