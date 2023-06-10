FROM nginx:1.21-alpine

COPY dev-proxy/nginx.conf /etc/nginx/templates/nginx.conf.template
# Copy the build output from the previous stage to the Nginx document root
#COPY mnt/c/Projekty/outfit-planner-mf/dist/apps/products /usr/share/nginx/html

# Expose port 80
EXPOSE 80

# Start Nginx
CMD ["nginx", "-g", "daemon off;"]