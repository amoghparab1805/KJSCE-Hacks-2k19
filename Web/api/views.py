from django.shortcuts import render
import pyrebase

from rest_framework.views import APIView
from django.http import HttpResponse, JsonResponse

config = {
    'apiKey': "AIzaSyDUP81oZRkZJS_TRYDnXelp9_TWNCj2OWA",
    'authDomain': "kjscehack-fa5ae.firebaseapp.com",
    'databaseURL': "https://kjscehack-fa5ae.firebaseio.com",
    'projectId': "kjscehack-fa5ae",
    'storageBucket': "",
    'messagingSenderId': "803546715173",
    'appId': "1:803546715173:web:ec618fcdabd348a278dbcc",
    'measurementId': "G-H4SLLNZ59N"
}

firebase = pyrebase.initialize_app(config)
auth = firebase.auth()


class UserSignIn(APIView):
    
    def post(self, request, *args, **kwargs):
        data = request.data
        
        return JSONResponse(data)
