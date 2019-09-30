from django.shortcuts import render
from rest_framework.views import APIView
from rest_framework import generics, viewsets, permissions, status
from django.http import HttpResponse, JsonResponse
from django.contrib.auth.hashers import make_password
from rest_framework.response import Response
import requests
import json
from .models import *
from .serializers import *

class SignUp(APIView):
    def post(self, request, *args, **kwargs):
        data = request.data
        username = data['uid']
        if('displayName' in data.keys()):
            display_name = data['displayName']
            first_name = display_name.split(' ')[0]
            last_name = display_name.split(' ')[1]
        elif('firstName' in data.keys() and 'lastName' in data.keys()):
            first_name = data['firstName']
            last_name = data['lastName']
        email = data['email']
        phone_number = data['phoneNumber']
        photo_url = data['photoURL']
        provider_id = data['providerId']
        password = "pass@123"
        hashed_password = make_password(password)
        try:
            user = User.objects.get(username=username)
        except User.DoesNotExist:
            user = User(
                username=username,
                first_name=first_name, 
                last_name=last_name,
                email=email,
                phone_number=phone_number,
                password=hashed_password,
                photo_url=photo_url,
                provider_id=provider_id
            )
            user.save()
        response = requests.post("http://localhost:8000/api/auth/token/login/", data={'username':username, 'password':password})
        token = response.json()
        return JsonResponse({
            'Success': 'Success',
            'user': user.first_name, # TODO : Make serializer for user
            'token': token["auth_token"]
        })
