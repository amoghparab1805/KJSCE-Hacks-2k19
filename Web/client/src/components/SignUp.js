import React, { Component } from "react"
import 'antd/dist/antd.css';
import '../index.css';
import { Layout, Menu, Breadcrumb, Icon } from 'antd';
import StyledFirebaseAuth from "react-firebaseui/StyledFirebaseAuth"
import axios from "axios"
import firebase from "../Firebase"
import WrappedDetailsForm from "./DetailsForm"

const { Header, Content, Footer, Sider } = Layout;
const { SubMenu } = Menu;

class SignUp extends Component {
  state = { 
    isSignedIn: false,
    collapsed: false,
    isNewUser:false,
    user: {},
  }
  uiConfig = {
    signInFlow: "popup",
    signInOptions: [
      firebase.auth.GoogleAuthProvider.PROVIDER_ID,
      {
        provider: firebase.auth.PhoneAuthProvider.PROVIDER_ID,
        recaptchaParameters: {
          type: 'image',
          size: 'normal',
          badge: 'bottomleft'
        },
        defaultCountry: 'IN'
      }
    ],
    callbacks: {
      signInSuccessWithAuthResult: async (authResult) => {
        await axios.post("https://red-hat-pirates.herokuapp.com/api/sign-up/", 
          JSON.stringify({ 
            displayName: authResult.user['providerData'][0]['displayName'],
            email: authResult.user['providerData'][0]['email'],
            phoneNumber: authResult.user['providerData'][0]['phoneNumber'],
            photoURL: authResult.user['providerData'][0]['photoURL'],
            providerId: authResult.user['providerData'][0]['providerId'],
            uid: authResult.user['uid'],
            age: null,
            gender: null
          }), 
          { headers: {"Content-Type": "application/json"} }
        ).then(response => {
          console.log(response.data)
          this.setState({user:response.data.user})
          localStorage.setItem("token", response.data.token)
          // localStorage.setItem("isNewUser", response.data.is_new_user)
          this.setState({isNewUser:response.data.is_new_user})
          localStorage.setItem("uid", response.data.user.username)
        })
      },
    }
  }

  nextPath(path) {
    this.props.history.push(path);
  }

  closeModal = (e) => {
    this.setState({
      showModal: false
    });
  };

  signOut = async() => {
    await axios.post("https://red-hat-pirates.herokuapp.com/api/auth/token/logout/", {},
    { headers: {"Authorization" : `Token ${localStorage.getItem("token")}`} }).then(response => {
      console.log("Successfully Logged Out")
      localStorage.removeItem("token")
    })
    localStorage.removeItem('token')
    firebase.auth().signOut()
  }

  onCollapse = collapsed => {
    console.log(collapsed);
    this.setState({ collapsed });
  };

  componentDidMount = () => {
    firebase.auth().onAuthStateChanged(async (user) => {
      this.setState({ isSignedIn: !!user })
      console.log("user", user)
      if(!!user){
        if(user['providerData'][0]['providerId'] === 'phone'){
          await axios.post("https://red-hat-pirates.herokuapp.com/api/sign-up/",{ 
            displayName: user['providerData'][0]['displayName'],
            email: null,
            phoneNumber: user['providerData'][0]['phoneNumber'],
            photoURL: null,
            providerId: "phone",
            uid: user['uid'],
            age: null,
            gender: null
          }, 
            { headers: {"Content-Type": "application/json"} }
          ).then(response => {
            console.log(response.data)
            this.setState({user:response.data.user})
            this.setState({isNewUser:response.data.is_new_user})
            localStorage.setItem("uid", response.data.user.username)
            localStorage.setItem("token", response.data.token)
            localStorage.removeItem("displayName")
          })
        }
      }
    })
  }

  render() {
    return (
    <div className="App">
        {this.state.isSignedIn ? (
          [
            (this.state.isNewUser ?
              (
                <WrappedDetailsForm/>
              ): (
                  <Layout style={{ minHeight: '100vh' }}>
                  <Sider collapsible collapsed={this.state.collapsed} onCollapse={this.onCollapse}>
                    <div className="logo" />
                    <Menu theme="dark" defaultSelectedKeys={['1']} mode="inline">
                      {this.state.collapsed ? (
                        <Menu.Item key="11" disabled style={{height:'50px'}}>
                          <img
                            alt="profile"
                            style={{marginTop:'10px',marginLeft:'-12px', width:'40px', height: '40px'}}
                            src={this.state.user.photoURL}
                          />
                        </Menu.Item>
                      ) : (
                        <Menu.Item key="9" disabled style={{height:'75px'}}>
                          <img
                              alt="profile"
                              style={{marginTop:'10px', width:'60px', height: '60px'}}
                              src={this.state.user.photoURL}
                            />&nbsp;&nbsp;
                          <span style={{fontSize:'14px', color:'white', fontWeight:'bold'}}>{this.state.user.displayName}</span>
                        </Menu.Item>
                      )}
                      <Menu.Item key="3" onClick={() => this.nextPath('/dashboard')} style={{height:'60px', fontSize:'18px'}}>
                        <Icon type="user" />
                        <span>User</span>
                      </Menu.Item>
                      <Menu.Item key="1" onClick={() => this.nextPath('/stocks')} style={{height:'60px', fontSize:'18px'}}>
                        <Icon type="stock" />
                        <span>Stocks</span>
                      </Menu.Item>
                      <Menu.Item key="5" onClick={() => this.nextPath('/investments')} style={{height:'60px', fontSize:'18px'}}>
                        <Icon type="pie-chart" />
                        <span>Investments</span>
                      </Menu.Item>
                      <Menu.Item key="2" onClick={() => this.nextPath('/customer-care')} style={{height:'60px', fontSize:'18px'}}>
                        <Icon type="desktop" />
                        <span>Customer Care</span>
                      </Menu.Item>
                      <Menu.Item key="4" onClick={this.signOut} style={{height:'60px', fontSize:'18px'}}>
                        <Icon type="sign-out" />
                        <span>Logout</span>
                      </Menu.Item>
                    </Menu>
                  </Sider>
                  <Layout>
                    <Header style={{ background: '#fff', padding: 0 }} />
                    <Content style={{ margin: '0 16px' }}>
                      <br/>
                      <div style={{ padding: 24, background: '#fff', minHeight: 360 }}>Bill is a cat.</div>
                    </Content>
                    <Footer style={{ textAlign: 'center' }}>Redhat Pirates ©2019 Created by Redhat Pirates</Footer>
                  </Layout>
                </Layout>
              )
            )
          ]
        ) : (
          <div>
            <StyledFirebaseAuth
              uiConfig={this.uiConfig}
              firebaseAuth={firebase.auth()}
            />
          </div>
        )}
      </div>
      )
    }
  }
  
export default SignUp  